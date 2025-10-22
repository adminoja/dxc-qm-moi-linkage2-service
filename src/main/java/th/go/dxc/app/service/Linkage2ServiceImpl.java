package th.go.dxc.app.service;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
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
import th.go.dxc.app.model.MoiDopaPersonFirstnameLastname;
import th.go.dxc.app.model.MoiDopaPor4License;
import th.go.dxc.app.model.MolDsdWorkforceDevelopment;
import th.go.dxc.app.model.MophNhsoHealthInsuranceRight;
import th.go.dxc.app.util.Linkage2ServiceImplMapper;
import th.go.dxc.app.model.AmloAssetFreezePerson;
import th.go.dxc.app.model.BaseMoiLinkage2;
import th.go.dxc.app.model.MsdhsDepCripple;
import th.go.dxc.app.model.JobLinkage2;
import th.go.dxc.app.model.Lk2Service;
import th.go.dxc.app.model.Lk2ServiceFilter;
import th.go.dxc.app.model.Lk2TokenService;
import th.go.dxc.app.model.LoginLinkage2Token;
import th.go.dxc.app.model.MoeOpsGraduate;
import th.go.dxc.app.model.MoeOpsStudent;
import th.go.dxc.app.model.MoiDopaAddress;
import th.go.dxc.app.model.MoiDopaAlien;
import th.go.dxc.app.model.MoiDopaBirthCertificate;
import th.go.dxc.app.model.MoiDopaDivorceCertificate;
import th.go.dxc.app.model.MoiDopaMarriageCertificate;
import th.go.dxc.infra.connector.dopalinkage2.model.request.CitizenSearchRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.Linkage2TokenRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.PersonProfileRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.response.AmloAssetFreezePersonResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MsdhsDepCrippleResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoeOpsGraduateResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoeOpsStudentResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaAddressResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaAlienResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaBirthCertificateResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaDivorceCertificateResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaMarriageCertificateResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaPersonChangeLastnamePrimaryResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaPersonChangeNamePrimaryResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaPersonFacePhotoResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaPersonFirstnameLastnameResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaPersonResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaPor4LicenseResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MoiDopaThaiIdCardResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MolDsdWorkforceDevelopmentResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.MophNhsoHealthInsuranceRightResponse;
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
	public Mono<Page<MoiDopaPerson>> findMoiDopaPerson(String userNin, String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoiDopaPersonResponse.class)
				.map(page -> {
					List<MoiDopaPerson> mappedList = page.getContent().stream()
							.map(item -> assignCitizenCardNumber(item, thaiNin, MoiDopaPerson.class))
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
							.map(item -> assignCitizenCardNumber(item, thaiNin, MoiDopaPersonChangeNamePrimary.class))
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
							.map(item -> assignCitizenCardNumber(item, thaiNin, MoiDopaPersonChangeLastnamePrimary.class))
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
								if (resp == null) return null; // <-- เช็ค resp ก่อน
								MoiDopaAlien alien = assignCitizenCardNumber(resp, thaiNin, MoiDopaAlien.class);
								
								// แปลง nested object (Father / Mother / Passport / Visa)
								// --- Father ---
								Optional.ofNullable(resp.getFather()).ifPresent(father -> {
									alien.setFatherPersonalID(father.getPersonalID());
									alien.setFatherName(father.getName());
									alien.setFatherNationalityDesc(father.getNationalityDesc());
								});
								// --- Mother ---
								Optional.ofNullable(resp.getMother()).ifPresent(mother -> {
									alien.setMotherPersonalID(mother.getPersonalID());
									alien.setMotherName(mother.getName());
									alien.setMotherNationalityDesc(mother.getNationalityDesc());
								});
								// --- Passport ---
								Optional.ofNullable(resp.getPassport()).ifPresent(passport -> {
									alien.setPassportDocumentType(passport.getDocumentType());
									alien.setPassportDocumentNo(passport.getDocumentNo());
									alien.setPassportIssuePlace(passport.getDocumentIssuePlace());
									alien.setPassportIssueDate(passport.getIssueDate());
									alien.setPassportExpireDate(passport.getExpireDate());
								});
								// --- Visa ---
								Optional.ofNullable(resp.getVisa()).ifPresent(visa -> {
									alien.setVisaDocumentNo(visa.getDocumentNo());
									alien.setVisaIssueDate(visa.getIssueDate());
									alien.setVisaExpireDate(visa.getExpireDate());
									alien.setVisaIssuePlace(visa.getDocumentIssuePlace());
									alien.setVisaType(visa.getVisaType());
									alien.setVisaRequestType(visa.getVisaRequestType());
								});
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
							.map(item -> assignCitizenCardNumber(item, thaiNin, MoiDopaDivorceCertificate.class))
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
							.map(item -> assignCitizenCardNumber(item, thaiNin, MoiDopaBirthCertificate.class))
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
							.map(item -> assignCitizenCardNumber(item, thaiNin, MoiDopaPersonFacePhoto.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}
	
	// ฐานข้อมูลทะเบียนสมรส
	@Override
	public Mono<Page<MoiDopaMarriageCertificate>> findMoiDopaMarriageCertificate(String userNin, String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoiDopaMarriageCertificateResponse.class)
				.map(page -> {
					List<MoiDopaMarriageCertificate> mappedList = page.getContent().stream()
							.map(item -> assignCitizenCardNumber(item, thaiNin, MoiDopaMarriageCertificate.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}
	

	// ฐานข้อมูลบัตรประจำตัวประชาชน
	@Override
	public Mono<Page<MoiDopaThaiIdCard>> findMoiDopaThaiIdCard(String userNin, String thaiNin, String jobId,
			String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoiDopaThaiIdCardResponse.class)
				.map(page -> {
					List<MoiDopaThaiIdCard> mappedList = page.getContent().stream()
							.map(obj -> objectMapper.convertValue(obj, MoiDopaThaiIdCardResponse.class)) // ✅ แปลงให้เป็น Response ชัดเจนก่อน
							.map(resp -> {
								if (resp == null) return null; // <-- เช็ค resp ก่อน
								MoiDopaThaiIdCard thaiIdCard = assignCitizenCardNumber(resp, thaiNin, MoiDopaThaiIdCard.class);
								
								// แปลง nested object
								// --- Document ---
								Optional.ofNullable(resp.getDocument()).ifPresent(document -> {
									thaiIdCard.setDocumentCountryDesc(document.getCountryDesc());
									thaiIdCard.setDocumentDistrictDesc(document.getDistrictDesc());
									thaiIdCard.setDocumentProvinceDesc(document.getProvinceDesc());
								});
								// --- NameEN ---
								Optional.ofNullable(resp.getNameEN()).ifPresent(nameEN -> {
									thaiIdCard.setNameENFirstName(nameEN.getFirstName());
									thaiIdCard.setNameENLastName(nameEN.getLastName());
									thaiIdCard.setNameENMiddleName(nameEN.getMiddleName());
									thaiIdCard.setNameENTitle(nameEN.getTitle());
								});
								// --- NameTH ---
								Optional.ofNullable(resp.getNameTH()).ifPresent(nameTH -> {
									thaiIdCard.setNameTHFullName(nameTH.getFullName());
									thaiIdCard.setNameTHFirstName(nameTH.getFirstName());
									thaiIdCard.setNameTHLastName(nameTH.getLastName());
									thaiIdCard.setNameTHMiddleName(nameTH.getMiddleName());
									thaiIdCard.setNameTHTitle(nameTH.getTitle());
								});
								// --- Address ---
								Optional.ofNullable(resp.getAddress()).ifPresent(address -> {
									thaiIdCard.setAddressAlleyDesc(address.getAlleyDesc());
									thaiIdCard.setAddressAlleyWayDesc(address.getAlleyWayDesc());
									thaiIdCard.setAddressDistrictDesc(address.getDistrictDesc());
									thaiIdCard.setAddressHouseNo(address.getHouseNo());
									thaiIdCard.setAddressProvinceDesc(address.getProvinceDesc());
									thaiIdCard.setAddressRoadDesc(address.getRoadDesc());
									thaiIdCard.setAddressSubdistrictDesc(address.getSubdistrictDesc());
									thaiIdCard.setAddressVillageNo(address.getVillageNo());
								});
								return thaiIdCard;
							})
							.filter(Objects::nonNull) // ตรวจแค่ object ทั้งตัว ว่าเป็น null หรือไม่
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}
	
	// ฐานข้อมูลทะเบียนบ้าน (บุคคลทุกประเภท)
	@Override
	public Mono<Page<MoiDopaAddress>> findMoiDopaAddress(String userNin, String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoiDopaAddressResponse.class)
				.map(page -> {
					List<MoiDopaAddress> mappedList = page.getContent().stream()
							.map(item -> assignCitizenCardNumber(item, thaiNin, MoiDopaAddress.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}

	// ฐานข้อมูลทะเบียนราษฎร (ค้นหาด้วยชื่อตัว-ชื่อสกุล)
	@Override
	public Mono<Page<MoiDopaPersonFirstnameLastname>> findMoiDopaPersonFirstnameLastname(String userNin, String firstName,
			String lastName, String recordNumber, String jobId, String departmentCode) {
		String limit = "1"; // fix 1 
		String middleName = ""; // ส่งค่าว่างไป
		return findByJobIdWithTokenByName(userNin, limit, firstName, lastName, middleName, recordNumber, jobId, departmentCode, MoiDopaPersonFirstnameLastnameResponse.class)
				.map(page -> {
					List<MoiDopaPersonFirstnameLastname> mappedList = page.getContent().stream()
							.map(item -> objectMapper.convertValue(item, MoiDopaPersonFirstnameLastname.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}

	// ฐานข้อมูลคนพิการ
	@Override
	public Mono<Page<MsdhsDepCripple>> findMsdhsDepCripple(String userNin, String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MsdhsDepCrippleResponse.class)
				.map(page -> {
					List<MsdhsDepCripple> mappedList = page.getContent().stream()
							.map(obj -> objectMapper.convertValue(obj, MsdhsDepCrippleResponse.class))
							.map(resp -> {
								if (resp == null) return null; // <-- เช็ค resp ก่อน
								MsdhsDepCripple cripple = assignCitizenCardNumber(resp, thaiNin, MsdhsDepCripple.class);
								
								// --- Result ---
								Optional.ofNullable(resp.getResult()).ifPresent(result -> {
									cripple.setBirthDate(result.getBirthDate());
									cripple.setCardExpireDate(result.getCardExpireDate());
									cripple.setCardIssueDate(result.getCardIssueDate());
									cripple.setDeformName(result.getDeformName());
									cripple.setPersonCode(result.getPersonCode());
									cripple.setPersonName(result.getPersonName());
								});
								return cripple;
							})
							.filter(Objects::nonNull) // ควรเปิดไว้ ป้องกัน resp เป็น null
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}

	// ฐานข้อมูลใบอนุญาตป.4
	@Override
	public Mono<Page<MoiDopaPor4License>> findMoiDopaPor4License(String userNin, String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoiDopaPor4LicenseResponse.class)
				.map(page -> {
					List<MoiDopaPor4License> mappedList = page.getContent().stream()
							.map(obj -> objectMapper.convertValue(obj, MoiDopaPor4LicenseResponse.class)) // ✅ แปลงให้เป็น Response ชัดเจนก่อน
							.flatMap(resp -> resp.getAllName() != null ? resp.getAllName().stream() : Stream.empty())
							.map(item -> assignCitizenCardNumber(item, thaiNin, MoiDopaPor4License.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}

	// ฐานข้อมูลสิทธิประกันสุขภาพและการลงทะเบียนกับหน่วยบริการ
	@Override
	public Mono<Page<MophNhsoHealthInsuranceRight>> findMophNhsoHealthInsuranceRight(String userNin, String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MophNhsoHealthInsuranceRightResponse.class)
				.map(page -> {
					List<MophNhsoHealthInsuranceRight> mappedList = page.getContent().stream()
							// 1) แปลง raw -> Response (มี nested WsDatetime)
							.map(obj -> objectMapper.convertValue(obj, MophNhsoHealthInsuranceRightResponse.class))
							.filter(res -> {
								if (res == null) return false;
								// ถ้า wsStatus = WS002 และคนอื่น ๆ null ให้ skip
								boolean allNullExceptPersonIdOrStatus =  ("WS002".equals(res.getWsStatus()) || res.getWsStatusDesc() != null);
								return !allNullExceptPersonIdOrStatus;
							})
							// 2) แปลง Response -> Target โดยใช้ BeanUtils + manual flatten
							.map(resp -> {
								if (resp == null) return null; // <-- เช็ค resp ก่อน
								log.debug(">>> Mapped response: {}", resp);
								// สร้าง target ใหม่
								// assign citizenCardNumber โดยใช้ helper generic
								MophNhsoHealthInsuranceRight healthInsuranceRight = assignCitizenCardNumber(resp, thaiNin, MophNhsoHealthInsuranceRight.class);

								// คัดลอก field ที่ชื่อเหมือนกัน (เช่น mainInscl, mainInsclName, personId, ...)
								BeanUtils.copyProperties(resp, healthInsuranceRight);
								
								// --- WsDatetime ---
								Optional.ofNullable(resp.getWsDatetime()).ifPresent(wsDatetime -> {
									healthInsuranceRight.setWsDatetimeDay(wsDatetime.getDay());
									healthInsuranceRight.setWsDatetimeHour(wsDatetime.getHour());
									healthInsuranceRight.setWsDatetimeMinute(wsDatetime.getMinute());
									healthInsuranceRight.setWsDatetimeMonth(wsDatetime.getMonth());
									healthInsuranceRight.setWsDatetimeSecond(wsDatetime.getSecond());
									healthInsuranceRight.setWsDatetimeTimezone(wsDatetime.getTimezone());
									healthInsuranceRight.setWsDatetimeYear(wsDatetime.getYear());
								});
								// --- NewDateRegister ---
//								Optional.ofNullable(resp.getNewDateRegister()).ifPresent(newDateRegister -> {
//									healthInsuranceRight.setNewDateRegisterDay(newDateRegister.getDay());
//									healthInsuranceRight.setNewDateRegisterHour(newDateRegister.getHour());
//									healthInsuranceRight.setNewDateRegisterMinute(newDateRegister.getMinute());
//									healthInsuranceRight.setNewDateRegisterMonth(newDateRegister.getMonth());
//									healthInsuranceRight.setNewDateRegisterSecond(newDateRegister.getSecond());
//									healthInsuranceRight.setNewDateRegisterTimezone(newDateRegister.getTimezone());
//									healthInsuranceRight.setNewDateRegisterYear(newDateRegister.getYear());
//								});
								return healthInsuranceRight;
							})
							.filter(Objects::nonNull) // ควรเปิดไว้ ป้องกัน resp เป็น null
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}
	
	// "ฐานข้อมูลรายชื่อบุคคลที่ถูกยึดหรืออายัดทรัพย์สิน (HR-02)
	@Override
	public Mono<Page<AmloAssetFreezePerson>> findAmloAssetFreezePerson(String userNin, String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, AmloAssetFreezePersonResponse.class)
				.map(page -> {
					List<AmloAssetFreezePerson> mappedList = page.getContent().stream()
							.map(item -> assignCitizenCardNumber(item, thaiNin, AmloAssetFreezePerson.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}

	// ฐานข้อมูลนักเรียน
	@Override
	public Mono<Page<MoeOpsStudent>> findMoeOpsStudent(String userNin, String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoeOpsStudentResponse.class)
				.map(page -> {
					List<MoeOpsStudent> mappedList = page.getContent().stream()
							.map(item -> assignCitizenCardNumber(item, thaiNin, MoeOpsStudent.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());

					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}

	// ฐานข้อมูลผู้สำเร็จการศึกษา
	@Override
	public Mono<Page<MoeOpsGraduate>> findMoeOpsGraduate(String userNin, String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MoeOpsGraduateResponse.class)
				.map(page -> {
					List<MoeOpsGraduate> mappedList = page.getContent().stream()
							.map(item -> assignCitizenCardNumber(item, thaiNin, MoeOpsGraduate.class))
							.filter(Linkage2ServiceImpl::hasMeaningfulData) // ใช้ dynamic check ทุก field
							.collect(Collectors.toList());
					return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
				});
	}

	// ฐานข้อมูลการพัฒนาฝีมือแรงงาน
	@Override
	public Mono<Page<MolDsdWorkforceDevelopment>> findMolDsdWorkforceDevelopment(String userNin, String thaiNin, String jobId, String departmentCode) {
		return findByJobIdWithToken(userNin, thaiNin, jobId, departmentCode, MolDsdWorkforceDevelopmentResponse.class)
				.map(page -> {
					List<MolDsdWorkforceDevelopment> mappedList = page.getContent().stream()
							.map(item -> assignCitizenCardNumber(item, thaiNin, MolDsdWorkforceDevelopment.class))
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
	
	// -------------------- Request สำหรับชื่อ-สกุล --------------------
	private <T> Mono<Page<Object>> findByJobIdWithTokenByName(String userNin, String limit, String firstName,
			String lastName, String middleName, String recordNumber, String jobId, String departmentCode,
			Class<T> responseClass) {

		return linkage2Token(userNin)
				.flatMap(list -> Mono.justOrEmpty(list.stream().findFirst())
						.switchIfEmpty(Mono.error(new IllegalStateException("No linkage2 token found for user"))))
				.flatMap(tokenEntity -> {
					String lk2Token = tokenEntity.getToken();
					String username = tokenEntity.getUsername();
					String sessionState = tokenEntity.getSessionState();
					
					return findByJobId(jobId).flatMap(lk2Service -> {
						String ipProxy = lk2Service.getIpProxy();
						CitizenSearchRequest req = citizenSearchRequest(List.of(lk2Service), jobId, limit, firstName, lastName, middleName, recordNumber);
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
		return new PersonProfileRequest(
				jobId,
				lk2ServiceList.stream()
					.map(s -> new PersonProfileRequest.DataReq(Integer.parseInt(s.getServiceId()),
						new PersonProfileRequest.QueryReq(thaiNin)))
					.collect(Collectors.toList())
		);
	}
	
	// -------------------- Request ชื่อตัว-ชื่อสกุล -------------------- 
	private CitizenSearchRequest citizenSearchRequest(List<Lk2ServiceEntity> lk2List, String jobId, String limit, String firstName, String lastName, String middleName
			, String recordNumber) {
		return new CitizenSearchRequest(
				jobId, 
				lk2List.stream()
					.map(lk2 -> new CitizenSearchRequest.DataReq(Integer.parseInt(lk2.getServiceId()),
							new CitizenSearchRequest.QueryReq(limit, firstName, lastName, middleName, recordNumber == null ? "1" : recordNumber)))
					.collect(Collectors.toList()));
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
	
	//  -------------------- Assign CitizenCardNumber (เลขบัตรประชาชนผู้ถูกค้น (thaiNin)) --------------------
	private <T extends BaseMoiLinkage2> T assignCitizenCardNumber(Object item, String thaiNin, Class<T> targetClass) {
		T model = objectMapper.convertValue(item, targetClass);
		model.setCitizenCardNumber(thaiNin);
		return model;
	}



}
