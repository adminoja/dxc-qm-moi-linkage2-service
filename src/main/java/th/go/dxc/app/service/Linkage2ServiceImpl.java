package th.go.dxc.app.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import reactor.core.publisher.Mono;
import th.go.dxc.app.model.LoginLinkage2Token;
import th.go.dxc.app.model.MoiDopaPerson;
import th.go.dxc.app.util.Linkage2ServiceImplMapper;
import th.go.dxc.app.model.JobLinkage2;
import th.go.dxc.app.model.Lk2Service;
import th.go.dxc.app.model.Lk2ServiceFilter;
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
import th.go.dxc.infra.datasource.dxcsamdb.lk2.repository.Lk2ServiceRepository;

@Slf4j
public class Linkage2ServiceImpl implements Linkage2Service {
	
	private final DopaLinkage2Service service;
	private final MapperFacade mapperFacade;
	private final Lk2ServiceRepository repository;
	private final Linkage2ServiceImplMapper mapper;
	
	public Linkage2ServiceImpl(DopaLinkage2Service service, MapperFacade mapperFacade, Lk2ServiceRepository repository,
			Linkage2ServiceImplMapper mapper) {
		super();
		this.service = service;
		this.mapperFacade = mapperFacade;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public Mono<LoginLinkage2> loginLinkage2(LoginLinkage2Request request) {
		return service.loginLinkage2(request).flatMap(res -> {
			// ตรวจ null แบบ reactive
			if (res.getOffice() == null) {
				log.error("Mapped LoginLinkage2 has null Office: {}", res);
				return Mono.error(new IllegalStateException("Office is null after mapping response"));
			}
			// map ต่อแบบ non-blocking
			return Mono.just(mapperFacade.map(res, LoginLinkage2.class));
		});
	}

	@Override
	public Mono<LoginLinkage2Token> confirmLoginLinkage2(ConfirmLoginLinkage2Request request) {
		return service.confirmLoginLinkage2(request).flatMap(res -> {
			// ตรวจ null แบบ reactive
			if (res.getToken()== null) {
				log.error("Mapped LoginLinkage2 has null Token: {}", res);
				return Mono.error(new IllegalStateException("Token is null after mapping response"));
			}
			// map ต่อแบบ non-blocking
			return Mono.just(mapperFacade.map(res, LoginLinkage2Token.class));
		});
	}

	@Override
	public Mono<LoginLinkage2Token> renewLoginLinkage2(Linkage2TokenRequest request) {
		return service.renewLoginLinkage2(request).flatMap(res -> {
			// ตรวจ null แบบ reactive
			if (res.getToken()== null) {
				log.error("Mapped LoginLinkage2 has null Token: {}", res);
				return Mono.error(new IllegalStateException("Token is null after mapping response"));
			}
			// map ต่อแบบ non-blocking
			return Mono.just(mapperFacade.map(res, LoginLinkage2Token.class));
		});
	}

	@Override
	public Mono<Void> logoutLinkage2(UsernameRequest request) {
		return service.logoutLinkage2(request)
				.then();
	}

	@Override
	public Mono<JobLinkage2> jobLinkage2(Linkage2TokenRequest request) {
		return service.jobLinkage2(request).flatMap(res -> {
			// ตรวจ null แบบ reactive
			if (res.getJob() == null) {
				log.error("Mapped Linkage2 has null Job: {}", res);
				return Mono.error(new IllegalStateException("Job is null after mapping response"));
			}
			// map ต่อแบบ non-blocking
			return Mono.just(mapperFacade.map(res, JobLinkage2.class));
		});
	}

	@Override
	public Page<Lk2Service> findAll(Lk2ServiceFilter filter, Pageable pageable) {
		Lk2ServiceEntityFilter entityFilter = mapper.mapEntityFilter(filter);
		Pageable entityPageable = mapper.mapEntityPageable(pageable);
		Page<Lk2ServiceEntity> entityPage = repository.findByFilterNative(entityFilter, entityPageable);
		Page<Lk2Service> resultPage = mapper.mapModelPage(entityPage);
		if (log.isDebugEnabled()) log.debug("lk2Service findAll: {}", entityPage);
		return resultPage;
	}
	
	// -------------------- ค้นหา linkage2 Token -------------------- 
//	private Lk2TokenService linkage2Token(String cid) {
//		Lk2TokenService lk2Token = lk2TokenRepository.findTopByUsernameOrderByIdDesc(cid)
//				.orElseThrow(() -> new DopaLinkage2Exception("ไม่พบ token ของ user: " + cid));
//		return lk2Token;
//	}
	
	// -------------------- ค้นหา linkage2 Service -------------------- 
	private List<Lk2ServiceEntity> linkage2Service(String jobId) {
		List<Lk2ServiceEntity> lk2List = repository.findByJobId(jobId);
//		if (lk2List.isEmpty()) {
//			throw new DopaLinkage2Exception("ไม่พบ service ของ jobId: " + jobId);
//		}
		return lk2List;
	}
	
	// -------------------- Request เลขบัตรประจำตัวประชาชน -------------------- 
	private PersonProfileRequest personProfileRequest(List<Lk2ServiceEntity> lk2List, String jobId, String thaiNin) {
		PersonProfileRequest req = new PersonProfileRequest();
		req.setJobID(jobId);
		req.setData(lk2List.stream()
				.map(lk2 -> new PersonProfileRequest.DataReq(
						Integer.parseInt(lk2.getServiceId()),
						new PersonProfileRequest.QueryReq(thaiNin)))
				.collect(Collectors.toList()));
		return req;
	}
	
//	@Override
//	public Page<ResponseItem<Object>> findMoiDopaPersons(String thaiNin, String jobId) {
////		Lk2TokenService lk2Token = linkage2Token(cid); // ดึง Token ล่าสุดของผู้ใช้งาน
////		List<Lk2Service> lk2List = linkage2Service(jobId); // ดึง Service ตาม jobId
//		PersonProfileRequest req = personProfileRequest(lk2List, jobId, thaiNin); // เตรียม Request
//		Map<Integer, Class<?>> responseMap = lk2List.stream() // map serviceID -> response class แบบ lambda
//				.collect(Collectors.toMap(
//					lk2 -> Integer.parseInt(lk2.getServiceId()), 
//					lk2 -> ProfileResponse.class,
//					(existing, replacement) -> existing  // ต้องใส่ merge function
//				));
//		return callService(req, lk2Token.getToken(), responseMap); // เรียกใช้งาน Generic callService
//	}
	
	// ฐานข้อมูลทะเบียนราษฎร (เลขบัตร)
	@Override
//	public Page<GenericResponse.ResponseItem<Object>> findMoiDopaPersons(String userNin, String thaiNin, String jobId, Pageable pageable) {
		public Mono<Page<ResponseItem<Object>>> findMoiDopaPersons(String userNin, String thaiNin, String jobId) {

		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyUGVyc29uYWxJRCI6MTEwMjAwMjY3MjIzNCwidXNlck9mZmljZUlEIjoxOTgsInByb3h5T2ZmaWNlSUQiOjE5OCwibG9naW5UeXBlIjoyLCJleHAiOjE3NTk5OTA3Njh9.RDk11Ukn_o8TkGU87Teu_8YJ1FvPwFfQwe2sqtOO2ss";
//		Lk2TokenService lk2Token = linkage2Token(cid); // ดึง Token ล่าสุดของผู้ใช้งาน
		List<Lk2ServiceEntity> lk2List = linkage2Service(jobId); // ดึง Service ตาม jobId
		PersonProfileRequest req = personProfileRequest(lk2List, jobId, thaiNin); // เตรียม Request
		Map<Integer, Class<?>> responseMap = lk2List.stream() // map serviceID -> response class แบบ lambda
				.collect(Collectors.toMap(
					lk2 -> Integer.parseInt(lk2.getServiceId()), 
					lk2 -> MoiDopaPerson.class,
					(existing, replacement) -> existing  // ต้องใส่ merge function
				));
		
//		return service.callService(req, lk2Token.getToken(), responseMap); // เรียกใช้งาน Generic callService;
		return service.callService(req, token, responseMap); // เรียกใช้งาน Generic callService;
	}
	
	
	
}
