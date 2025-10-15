package th.go.dxc.app.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import th.go.dxc.app.model.LoginLinkage2Token;
import th.go.dxc.app.model.MoiDopaPerson;
import th.go.dxc.app.util.Linkage2ServiceImplMapper;
import th.go.dxc.app.model.JobLinkage2;
import th.go.dxc.app.model.Lk2Service;
import th.go.dxc.app.model.Lk2ServiceFilter;
import th.go.dxc.app.model.Lk2TokenService;
import th.go.dxc.app.model.LoginLinkage2;
import th.go.dxc.infra.connector.dopalinkage2.model.request.ConfirmLoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.Linkage2TokenRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.PersonProfileRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.UsernameRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse.ResponseItem;
import th.go.dxc.infra.connector.dopalinkage2.service.DopaLinkage2Service;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntityFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2TokenServiceEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.repository.Lk2ServiceRepository;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.repository.Lk2TokenServiceRepository;

@Slf4j
public class Linkage2ServiceImpl implements Linkage2Service {
	
	private final DopaLinkage2Service service;
	private final MapperFacade mapperFacade;
	private final Lk2ServiceRepository repository;
	private final Linkage2ServiceImplMapper mapper;
	private final Lk2TokenServiceRepository lk2TokenServiceRepository;
	
	public Linkage2ServiceImpl(DopaLinkage2Service service, MapperFacade mapperFacade, Lk2ServiceRepository repository,
			Linkage2ServiceImplMapper mapper, Lk2TokenServiceRepository lk2TokenServiceRepository) {
		super();
		this.service = service;
		this.mapperFacade = mapperFacade;
		this.repository = repository;
		this.mapper = mapper;
		this.lk2TokenServiceRepository = lk2TokenServiceRepository;
	}

	// -------------------- ค้นหา JobLinkage2 -------------------- 
	@Override
	public Mono<JobLinkage2> jobLinkage2(Linkage2TokenRequest request, String departmentCode) {
		return findByDepartmentCodeLk2Service(departmentCode)
				.flatMap(lk2Service -> lk2Service.stream().findFirst()
						.map(Mono::just)
						.orElseGet(() -> Mono.error(new IllegalArgumentException("No lk2Service found for departmentCode: " + departmentCode)))
				)
				.flatMap(lk2ServiceEntity -> {
					if (!StringUtils.hasText(lk2ServiceEntity.getDepartmentCode())) {
						return Mono.error(new IllegalArgumentException("DepartmentCode not set in lk2ServiceEntity"));
					}
					// ดึง ipProxy
					String ipProxy = lk2ServiceEntity.getIpProxy();
					// log ดูเพื่อความชัวร์
					log.debug("Using ipProxy [{}] for departmentCode [{}]", ipProxy, departmentCode);
					
					return service.jobLinkage2(request, ipProxy)
							.flatMap(res -> {
								// ตรวจ null แบบ reactive
								if (res.getJob() == null) {
									log.error("Mapped Linkage2 has null Job: {}", res);
									return Mono.error(new IllegalStateException("Job is null after mapping response"));
								}
								// map ต่อแบบ non-blocking
								return Mono.just(mapperFacade.map(res, JobLinkage2.class));
							});
				});
	}
	
	// -------------------- ค้นหา Lk2Service findAll -------------------- 
	@Override
	public Mono<Page<Lk2Service>> findAll(Lk2ServiceFilter filter, Pageable pageable) {
		return Mono.fromCallable(() -> {
			Lk2ServiceEntityFilter entityFilter = mapper.mapEntityFilter(filter);
			Pageable entityPageable = mapper.mapEntityPageable(pageable);
			Page<Lk2ServiceEntity> entityPage = repository.findByFilterNative(entityFilter, entityPageable);
			Page<Lk2Service> resultPage = mapper.mapModelPage(entityPage);
			if (log.isDebugEnabled())
				log.debug("lk2Service findAll: {}", entityPage);
			return resultPage;
		}).subscribeOn(Schedulers.boundedElastic());
	}
	
	// ฐานข้อมูลทะเบียนราษฎร (เลขบัตร)
	@Override
	public Mono<Page<GenericResponse.ResponseItem<Object>>> findMoiDopaPersons(String userNin, String thaiNin, String jobId) {
		// ดึง Token ล่าสุดของผู้ใช้งาน
		return linkage2Token(userNin)
				.flatMap(lk2TokenList -> Mono.justOrEmpty(lk2TokenList.stream().findFirst())
						.switchIfEmpty(Mono.error(new IllegalStateException("No linkage2 token found for user"))))
				.flatMap(lk2TokenEntity -> {
					String lk2Token = lk2TokenEntity.getToken();
					
					// ดึง Service ตาม jobId
					return findByJobIdLk2Service(jobId)
							.flatMap(lk2ServiceList -> Mono.justOrEmpty(lk2ServiceList.stream().findFirst())
									.switchIfEmpty(Mono.error(new IllegalStateException("No Lk2Service found for jobId: " + jobId)))
									.flatMap(firstService -> {
										String ipProxy = firstService.getIpProxy();
										PersonProfileRequest req = personProfileRequest(lk2ServiceList, jobId, thaiNin); // เตรียม Request
										Map<Integer, Class<?>> responseMap = lk2ServiceList.stream() // map serviceID -> response class แบบ lambda
												.collect(Collectors.toMap(
													lk2 -> Integer.parseInt(lk2.getServiceId()), 
													lk2 -> MoiDopaPerson.class,
													(existing, replacement) -> existing  // ต้องใส่ merge function
												));
										return service.callService(req, lk2Token, responseMap, ipProxy)
												.subscribeOn(Schedulers.boundedElastic()); // เรียกใช้งาน Generic callService;
							}));
				});
	}
	
	// -------------------- Request เลขบัตรประจำตัวประชาชน -------------------- 
	private PersonProfileRequest personProfileRequest(List<Lk2ServiceEntity> lk2ServiceList, String jobId, String thaiNin) {
		PersonProfileRequest req = new PersonProfileRequest();
		req.setJobID(jobId);
		req.setData(lk2ServiceList.stream()
				.map(lk2 -> new PersonProfileRequest.DataReq(
						Integer.parseInt(lk2.getServiceId()),
						new PersonProfileRequest.QueryReq(thaiNin)))
				.collect(Collectors.toList()));
		return req;
	}
	
	
	// -------------------- ค้นหา Lk2TokenService โดย username(เลขบัตร ปปช) -------------------- 
	private Mono<List<Lk2TokenServiceEntity>> linkage2Token(String username) {
		return Mono.fromCallable(() -> {
			List<Lk2TokenServiceEntity> lk2Token = lk2TokenServiceRepository.findByUsername(username);
			if (lk2Token.isEmpty()) {
				throw new IllegalArgumentException("Lk2Service token not found for user");
			}
			return lk2Token;
		}).subscribeOn(Schedulers.boundedElastic()); // ✅ ป้องกัน block event loop
	}
	
	// -------------------- ค้นหา Lk2Service โดย jobId -------------------- 
	private Mono<List<Lk2ServiceEntity>> findByJobIdLk2Service(String jobId) {
		return Mono.fromCallable(() -> {
			List<Lk2ServiceEntity> lk2List = repository.findByJobId(jobId);
			if (lk2List.isEmpty()) {
				throw new IllegalArgumentException("Lk2Service data not found for jobId: " + jobId);
			}
			return lk2List;
		}).subscribeOn(Schedulers.boundedElastic()); // ✅ ป้องกัน block event loop
	}
	
	// -------------------- ค้นหา Lk2Service โดย DepartmentCode -------------------- 
	@Override
	public Mono<List<Lk2ServiceEntity>> findByDepartmentCodeLk2Service(String departmentCode) {
		return Mono.fromCallable(() -> {
			List<Lk2ServiceEntity> lk2Service = repository.findByDepartmentCode(departmentCode);
			if (lk2Service.isEmpty()) {
				throw new IllegalArgumentException("Lk2Service data not found for departmentCode: " + departmentCode);
			}
			return lk2Service;
		}).subscribeOn(Schedulers.boundedElastic()); // ✅ ป้องกัน block event loop
	}
	
	//  -------------------- เอามา log ดู Keycloak -------------------- 
	@Override
	public String departmentCodeKeycloakFromToken() {
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				.filter(auth -> auth instanceof JwtAuthenticationToken)
				.map(auth -> (JwtAuthenticationToken) auth)
				.map(jwtAuth -> {
					Map<String, Object> attributes = jwtAuth.getTokenAttributes();
					log.debug("Token attributes: {}", attributes); // 👉 log ออกมาทั้ง Map
					return String.valueOf(attributes.get("departmentCode"));
				})
				.orElseThrow(() -> new AuthenticationServiceException("Missing departmentCode in token"));
	}
	
}
