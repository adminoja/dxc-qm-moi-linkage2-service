package th.go.dxc.app.service;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import th.go.dxc.app.model.MoiDopaPerson;
import th.go.dxc.app.model.MoiDopaPersonChangeLastnamePrimary;
import th.go.dxc.app.model.MoiDopaPersonChangeNamePrimary;
import th.go.dxc.app.model.MoiDopaPersonFacePhoto;
import th.go.dxc.app.model.MolDsdWorkforceDevelopment;
import th.go.dxc.app.util.Linkage2ServiceImplMapper;
import th.go.dxc.app.model.JobLinkage2;
import th.go.dxc.app.model.Lk2Service;
import th.go.dxc.app.model.Lk2ServiceFilter;
import th.go.dxc.app.model.Lk2TokenService;
import th.go.dxc.app.model.LoginLinkage2Token;
import th.go.dxc.app.model.MoeOpsGraduate;
import th.go.dxc.app.model.MoeOpsStudent;
import th.go.dxc.app.model.MoiDopaAlien;
import th.go.dxc.app.model.MoiDopaBirthCertificate;
import th.go.dxc.app.model.MoiDopaDivorceCertificate;
import th.go.dxc.app.model.MoiDopaMarriageCertificate;
import th.go.dxc.infra.connector.dopalinkage2.model.request.Linkage2TokenRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.PersonProfileRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoeOpsGraduateResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoeOpsStudentResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaAlienResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaBirthCertificateResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaDivorceCertificateResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaMarriageCertificateResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaPersonChangeLastnamePrimaryResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaPersonChangeNamePrimaryResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaPersonFacePhotoResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaPersonResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MolDsdWorkforceDevelopmentResponse;
import th.go.dxc.infra.connector.dopalinkage2.service.DopaLinkage2Service;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntityFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2TokenServiceEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.repository.Lk2ServiceRepository;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.repository.Lk2TokenServiceRepository;
import th.go.dxc.share.security.service.SecurityService;

@Slf4j
public class Linkage2ServiceImpl implements Linkage2Service {
	
	private final DopaLinkage2Service service;
	private final MapperFacade mapperFacade;
	private final Lk2ServiceRepository repository;
	private final Linkage2ServiceImplMapper mapper;
	private final Lk2TokenServiceRepository lk2TokenServiceRepository;
	private final LoginLinkage2Service loginLinkage2Service;
	private final Lk2TokenServiceService lk2TokenServiceService;
	private final ObjectMapper objectMapper;
	
	public Linkage2ServiceImpl(DopaLinkage2Service service, MapperFacade mapperFacade, Lk2ServiceRepository repository,
			Linkage2ServiceImplMapper mapper, Lk2TokenServiceRepository lk2TokenServiceRepository, SecurityService securityService,
			LoginLinkage2Service loginLinkage2Service, Lk2TokenServiceService lk2TokenServiceService,
			ObjectMapper objectMapper) {
		super();
		this.service = service;
		this.mapperFacade = mapperFacade;
		this.repository = repository;
		this.mapper = mapper;
		this.lk2TokenServiceRepository = lk2TokenServiceRepository;
		this.loginLinkage2Service = loginLinkage2Service;
		this.lk2TokenServiceService = lk2TokenServiceService;
		this.objectMapper = objectMapper;
	}

	// -------------------- ค้นหา JobLinkage2 -------------------- 
	@Override
	public Mono<JobLinkage2> jobLinkage2(Linkage2TokenRequest request, String departmentCode) {
		// ตรวจสอบค่าเบื้องต้น
		if (!StringUtils.hasText(departmentCode)) {
			log.error("DepartmentCode must not be empty");
			return Mono.error(new IllegalStateException("DepartmentCode must not be empty"));
		}
		
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
	
	// -------------------- เช็ค linkage2 token หมดอายุ และ ต่ออายุพร้อมบันทึก  -------------------- 
	private Mono<String> linkage2TokenRenew (String token, String cid, String departmentCode, String sessionStateKc) {
		return Mono.defer(() -> {
			if (!StringUtils.hasText(cid)) {
				throw new IllegalArgumentException("CID must not be empty");
			}
			
			List<Lk2TokenServiceEntity> existingTokenList = lk2TokenServiceRepository
					.findByUsernameAndSessionStateKcOrderByIdDesc(cid, sessionStateKc);
			
			if (existingTokenList == null || existingTokenList.isEmpty()) {
				return Mono.empty();
			}
			
			Lk2TokenServiceEntity existingToken = existingTokenList.get(0);
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime expireTime = existingToken.getExpireTime();

			// ถ้า expireTime ยังเหลือเกิน 10 นาที (เช่น expireTime - now > 10 นาที) →
			// ถ้ายังเหลือมากกว่า 10 นาที → ใช้ token เดิม
			if (expireTime != null) {
				long minutesUntilExpire = Duration.between(now, expireTime).toMinutes();
				if (minutesUntilExpire > 10) { // หมายถึงยังไม่ถึง 50 นาที
					// อัพเดต lastActiveTime ของ token เดิม
					log.info("✅ Token still valid for user: {} (expires in {} min)", cid, minutesUntilExpire);
					return lk2TokenServiceService.updateLastActiveTime(existingToken.getUsername(), existingToken.getSessionState())
							.thenReturn(existingToken.getToken());
				}
			}
		
		
			// ---------- ถ้าเกิน 50 นาที (เหลือต่ำกว่า 10 นาที) → ต่ออายุ token ----------
			Linkage2TokenRequest renewReq = new Linkage2TokenRequest();
			renewReq.setToken(token);
			
			return loginLinkage2Service.renewLoginLinkage2(renewReq, departmentCode)
					.flatMap(res -> {
						String newToken = res.getToken();
						if (!StringUtils.hasText(newToken)) {
							return Mono.error(new IllegalStateException("Renew token failed — empty token"));
						}
						
						// บันทึก token ใหม่
						Lk2TokenService lk2NewToken = new Lk2TokenService();
						lk2NewToken.setUsername(cid);
						lk2NewToken.setToken(newToken);
						lk2NewToken.setInsertTime(LocalDateTime.now());
						lk2NewToken.setChannel("9"); // ไม่รู้ยังต้องใช้อยู่ไหม 9 = renew linkage2 token
						lk2NewToken.setSessionState(sessionStateKc); // อาจไม่ต้องใช้แล้ว
						lk2NewToken.setLastActiveTime(LocalDateTime.now()); // ไม่รู้ยังต้องใช้อยู่ไหม
						lk2TokenServiceService.insert(lk2NewToken);
						
						log.info("♻️ Renewed new Linkage2 token for user: {}", cid);
						return Mono.just(newToken);
					});
		});
	}

	// ฐานข้อมูลทะเบียนราษฎร
	@Override
	public Mono<Page<MoiDopaPerson>> findMoiDopaPersons(String userNin, String thaiNin, String jobId, String departmentCode) {
//		Mono<Page<Object>> rawPageMono = findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoiDopaPersonResponse.class);
//		// ใช้ method reactive ของ Linkage2ServiceImplMapper
//		return mapper.mapPageMono(rawPageMono, MoiDopaPerson.class);
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoiDopaPersonResponse.class)
				.map(page -> {
					List<MoiDopaPerson> mappedList = page.getContent().stream()
							.map(item -> objectMapper.convertValue(item, MoiDopaPerson.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}

	// ฐานข้อมูลการจดทะเบียนเปลี่ยนชื่อตัว
	@Override
	public Mono<Page<MoiDopaPersonChangeNamePrimary>> findMoiDopaPersonChangeNamePrimary(String userNin, String thaiNin,
			String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoiDopaPersonChangeNamePrimaryResponse.class)
				.map(page -> {
					List<MoiDopaPersonChangeNamePrimary> mappedList = page.getContent().stream()
							.map(obj -> objectMapper.convertValue(obj, MoiDopaPersonChangeNamePrimaryResponse.class)) // ✅ แปลงให้เป็น Response ชัดเจนก่อน
							.flatMap(resp -> resp.getAllName() != null ? resp.getAllName().stream() : Stream.empty())
							.map(item -> objectMapper.convertValue(item, MoiDopaPersonChangeNamePrimary.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());

					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}

	// ฐานข้อมูลการจดทะเบียนเปลี่ยนชื่อสกุล
	@Override
	public Mono<Page<MoiDopaPersonChangeLastnamePrimary>> findDopaPersonChangeLastnamePrimary(String userNin,
			String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoiDopaPersonChangeLastnamePrimaryResponse.class)
				.map(page -> {
					List<MoiDopaPersonChangeLastnamePrimary> mappedList = page.getContent().stream()
							.map(obj -> objectMapper.convertValue(obj, MoiDopaPersonChangeLastnamePrimaryResponse.class)) // ✅ แปลงให้เป็น Response ชัดเจนก่อน
							.flatMap(resp -> resp.getAllName() != null ? resp.getAllName().stream() : Stream.empty())
							.map(item -> objectMapper.convertValue(item, MoiDopaPersonChangeLastnamePrimary.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());

					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}
	
	// ฐานข้อมูลทะเบียนบุคคลต่างด้าว
	@Override
	public Mono<Page<MoiDopaAlien>> findMoiDopaAlien(String userNin, String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoiDopaAlienResponse.class)
				.map(page -> {
					List<MoiDopaAlien> mappedList = page.getContent().stream()
							.map(obj -> objectMapper.convertValue(obj, MoiDopaAlienResponse.class)) // ✅ แปลงให้เป็น Response ชัดเจนก่อน
							.map(resp -> {
								MoiDopaAlien alien = objectMapper.convertValue(resp, MoiDopaAlien.class);
								// แปลง nested object (Father / Mother / Passport / Visa)
								if (resp.getFather() != null) {
									alien.setFatherPersonalID(resp.getFather().getPersonalID());
									alien.setFatherName(resp.getFather().getName());
									alien.setFatherNationalityDesc(resp.getFather().getNationalityDesc());
								}
								if (resp.getMother() != null) {
									alien.setMotherPersonalID(resp.getMother().getPersonalID());
									alien.setMotherName(resp.getMother().getName());
									alien.setMotherNationalityDesc(resp.getMother().getNationalityDesc());
								}
								if (resp.getPassport() != null) {
									alien.setPassportDocumentType(resp.getPassport().getDocumentType());
									alien.setPassportDocumentNo(resp.getPassport().getDocumentNo());
									alien.setPassportIssuePlace(resp.getPassport().getDocumentIssuePlace());
									alien.setPassportIssueDate(resp.getPassport().getIssueDate());
									alien.setPassportExpireDate(resp.getPassport().getExpireDate());
								}
								if (resp.getVisa() != null) {
									alien.setVisaDocumentNo(resp.getVisa().getDocumentNo());
									alien.setVisaIssueDate(resp.getVisa().getIssueDate());
									alien.setVisaExpireDate(resp.getVisa().getExpireDate());
									alien.setVisaIssuePlace(resp.getVisa().getDocumentIssuePlace());
									alien.setVisaType(resp.getVisa().getVisaType());
									alien.setVisaRequestType(resp.getVisa().getVisaRequestType());
								}
								return alien;
							})
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());

					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}
	
	// ฐานข้อมูลทะเบียนการหย่า
	@Override
	public Mono<Page<MoiDopaDivorceCertificate>> findMoiDopaDivorceCertificate(String userNin, String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoiDopaDivorceCertificateResponse.class)
				.map(page -> {
					List<MoiDopaDivorceCertificate> mappedList = page.getContent().stream()
							.map(item -> objectMapper.convertValue(item, MoiDopaDivorceCertificate.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}

	// ฐานข้อมูลใบสูติบัตร
	@Override
	public Mono<Page<MoiDopaBirthCertificate>> findMoiDopaBirthCertificate(String userNin, String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoiDopaBirthCertificateResponse.class)
				.map(page -> {
					List<MoiDopaBirthCertificate> mappedList = page.getContent().stream()
							.map(item -> objectMapper.convertValue(item, MoiDopaBirthCertificate.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}

	// ฐานข้อมูลภาพใบหน้า
	@Override
	public Mono<Page<MoiDopaPersonFacePhoto>> findMoiDopaPersonFacePhoto(String userNin, String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoiDopaPersonFacePhotoResponse.class)
				.map(page -> {
					List<MoiDopaPersonFacePhoto> mappedList = page.getContent().stream()
							.map(item -> objectMapper.convertValue(item, MoiDopaPersonFacePhoto.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}
	
	// ฐานข้อมูลทะเบียนสมรส
	@Override
	public Mono<Page<MoiDopaMarriageCertificate>> findMoiDopaMarriageCertificate(String userNin, String thaiNin,
			String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoiDopaMarriageCertificateResponse.class)
				.map(page -> {
					List<MoiDopaMarriageCertificate> mappedList = page.getContent().stream()
							.map(item -> objectMapper.convertValue(item, MoiDopaMarriageCertificate.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}
	
	// ฐานข้อมูลนักเรียน
	@Override
	public Mono<Page<MoeOpsStudent>> findMoeOpsStudent(String userNin, String thaiNin, String jobId, String departmentCode) {
//		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoeOpsStudent.class);
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoeOpsStudentResponse.class)
				.map(page -> {
					List<MoeOpsStudent> mappedList = page.getContent().stream()
							.map(item -> objectMapper.convertValue(item, MoeOpsStudent.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());

					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}

	// ฐานข้อมูลผู้สำเร็จการศึกษา
	@Override
	public Mono<Page<MoeOpsGraduate>> findMoeOpsGraduate(String userNin, String thaiNin, String jobId, String departmentCode) {
//		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoeOpsGraduate.class);
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoeOpsGraduateResponse.class)
				.map(page -> {
					List<MoeOpsGraduate> mappedList = page.getContent().stream()
							.map(item -> objectMapper.convertValue(item, MoeOpsGraduate.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}

	// ฐานข้อมูลการพัฒนาฝีมือแรงงาน
	@Override
	public Mono<Page<MolDsdWorkforceDevelopment>> findMolDsdWorkforceDevelopment(String userNin, String thaiNin, String jobId, String departmentCode) {
//		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MolDsdWorkforceDevelopment.class);
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MolDsdWorkforceDevelopmentResponse.class)
				.map(page -> {
					List<MolDsdWorkforceDevelopment> mappedList = page.getContent().stream()
							.map(item -> objectMapper.convertValue(item, MolDsdWorkforceDevelopment.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}


	// -------------------- Request สำหรับเลขบัตรประจำตัวประชาชน --------------------
	private <T> Mono<Page<Object>> findByJobIdWithToken(String userNin, String thaiNin, String jobId,
			String departmentCode, Class<T> responseClass) {

		return linkage2Token(userNin)
				.flatMap(list -> Mono.justOrEmpty(list.stream().findFirst())
						.switchIfEmpty(Mono.error(new IllegalStateException("No linkage2 token found for user"))))
				.flatMap(tokenEntity -> {
					String lk2Token = tokenEntity.getToken();
					String username = tokenEntity.getUsername();
					String sessionState = tokenEntity.getSessionState();

					return findByJobId(jobId).flatMap(lk2Service -> {
						String ipProxy = lk2Service.getIpProxy();
						PersonProfileRequest req = personProfileRequest(List.of(lk2Service), jobId, thaiNin);
						Map<Integer, Class<?>> responseMap = Map.of(Integer.parseInt(lk2Service.getServiceId()),
								responseClass);

						return linkage2TokenRenew(lk2Token, username, departmentCode, sessionState).flatMap(
								validToken -> service.callService(req, validToken, responseMap, ipProxy).map(page -> {
									List<Object> content = page.getContent().stream()
											.map(GenericResponse.ResponseItem::getResponseData)
											.collect(Collectors.toList());
									return new PageImpl<>(content);
								}));
					});
				});
	}

	
	// -------------------- Request เลขบัตรประจำตัวประชาชน -------------------- 
	private PersonProfileRequest personProfileRequest(List<Lk2ServiceEntity> lk2ServiceList, String jobId, String thaiNin) {
//		PersonProfileRequest req = new PersonProfileRequest();
//		req.setJobID(jobId);
//		req.setData(lk2ServiceList.stream()
//				.map(lk2 -> new PersonProfileRequest.DataReq(
//						Integer.parseInt(lk2.getServiceId()),
//						new PersonProfileRequest.QueryReq(thaiNin)))
//				.collect(Collectors.toList()));
//		return req;
		return new PersonProfileRequest(
				jobId,
				lk2ServiceList.stream()
					.map(s -> new PersonProfileRequest.DataReq(
							Integer.parseInt(s.getServiceId()),
						new PersonProfileRequest.QueryReq(thaiNin)))
					.collect(Collectors.toList())
		);
	}
	
	// -------------------- Generic Mapper กลาง --------------------
	private <R, M> Mono<Page<M>> mapPage(Mono<Page<Object>> pageMono, Class<R> responseClass,
			Function<R, Stream<?>> extractor, Function<Object, M> converter) {

		return pageMono.map(page -> {
			List<M> mappedList = page.getContent().stream().map(obj -> objectMapper.convertValue(obj, responseClass))
					.flatMap(resp -> extractor.apply(resp)).map(item -> converter.apply(item))
					.filter(Linkage2ServiceImpl::hasMeaningfulData) // ✅ ฟิลเตอร์ข้อมูลที่ไม่มีค่า
					.collect(Collectors.toList());

			return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
		});
	}

	
	
	// -------------------- ค้นหา Lk2TokenService โดย username(เลขบัตร ปปช) -------------------- 
	private Mono<List<Lk2TokenServiceEntity>> linkage2Token(String username) {
		return Mono.fromCallable(() -> {
			List<Lk2TokenServiceEntity> lk2Token = lk2TokenServiceRepository.findByUsernameOrderByIdDesc(username);
			if (lk2Token.isEmpty()) {
				throw new IllegalArgumentException("Lk2Service token not found for user");
			}
			return lk2Token;
		}).subscribeOn(Schedulers.boundedElastic()); // ✅ ป้องกัน block event loop
	}
	
	// -------------------- ค้นหา Lk2Service โดย jobId -------------------- 
	public Mono<Lk2ServiceEntity> findByJobId(String jobId) {
		return Mono.fromCallable(() -> repository.findByJobId(jobId)
				.orElseThrow(() -> new IllegalArgumentException("Lk2Service data not found for jobId: " + jobId))
				).subscribeOn(Schedulers.boundedElastic());
	}
	
	// -------------------- ค้นหา Lk2Service โดย serviceId และ departmentCode -------------------- 
	@Override
	public Mono<Lk2ServiceEntity> findByServiceIdAndDepartmentCode(String serviceId, String departmentCode) {
		return Mono.fromCallable(() -> repository.findByServiceIdAndDepartmentCode(serviceId, departmentCode)
				.orElseThrow(() -> new IllegalArgumentException("Lk2Service data not found for serviceId: " + serviceId 
						+ " and departmentCode: " + departmentCode))
				).subscribeOn(Schedulers.boundedElastic());
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
	
	// -------------------- ตรวจทุก field ของ object ว่ามีข้อมูลจริงหรือไม่ -------------------- 
	public static boolean hasMeaningfulData(Object obj) {
		if (obj == null)
			return false;
		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				Object value = field.get(obj);
				if (value != null && !(value instanceof String && ((String) value).isBlank())) {
					return true; // มี field ที่ไม่ null และไม่ว่าง
				}
			} catch (IllegalAccessException e) {
				// ignore
			}
		}
		return false; // ทุก field เป็น null/ว่าง
	}

}
