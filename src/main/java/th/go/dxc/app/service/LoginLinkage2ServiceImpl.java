package th.go.dxc.app.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import th.go.dxc.app.model.Lk2TokenService;
import th.go.dxc.app.model.LoginLinkage2;
import th.go.dxc.app.model.LoginLinkage2Token;
import th.go.dxc.app.model.Result;
import th.go.dxc.infra.connector.dopalinkage2.model.request.ConfirmLoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.Linkage2TokenRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.UsernameRequest;
import th.go.dxc.infra.connector.dopalinkage2.service.DopaLinkage2Service;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2TokenServiceEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.repository.Lk2ThaidLogRepository;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.repository.Lk2TokenServiceRepository;
import th.go.dxc.infra.datasource.dxcsamdb.useraccount.entity.DxcUserAccountEntity;
import th.go.dxc.infra.datasource.dxcsamdb.useraccount.repository.DxcUserAccountRepository;
import th.go.dxc.share.security.service.SecurityService;

@Slf4j
public class LoginLinkage2ServiceImpl implements LoginLinkage2Service {
	
	private final DopaLinkage2Service service;
	private final MapperFacade mapperFacade;
	private final Lk2ThaidLogRepository lk2ThaidLogRepository;
	private final Lk2TokenServiceService lk2TokenServiceService;
	private final Linkage2Service linkage2Service;
	private final Lk2TokenServiceRepository repository;
	private final DxcUserAccountRepository userAccountRepository;
	
	public LoginLinkage2ServiceImpl(DopaLinkage2Service service, MapperFacade mapperFacade, 
			Lk2ThaidLogRepository lk2ThaidLogRepository, Lk2TokenServiceService lk2TokenServiceService,
			Linkage2Service linkage2Service, Lk2TokenServiceRepository repository, DxcUserAccountRepository userAccountRepository) {
		super();
		this.service = service;
		this.mapperFacade = mapperFacade;
		this.lk2ThaidLogRepository = lk2ThaidLogRepository;
		this.lk2TokenServiceService = lk2TokenServiceService;
		this.linkage2Service = linkage2Service;
		this.repository = repository;
		this.userAccountRepository = userAccountRepository;
	}
	
	private Mono<List<Lk2ServiceEntity>> findByDepartmentCodeLk2Service(String departmentCode) {
		Mono<List<Lk2ServiceEntity>> services = linkage2Service.findByDepartmentCodeLk2Service(departmentCode);
		return services;
	}
	
	// -------------------- ขอเข้าใช้งาน --------------------
	@Override
	public Mono<LoginLinkage2> loginLinkage2(LoginLinkage2Request request, String departmentCode) {
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
					
					return service.loginLinkage2(request, ipProxy).
							flatMap(res -> {
								// ตรวจ null แบบ reactive
								if (res.getOffice() == null) {
									log.error("Mapped LoginLinkage2 has null Office: {}", res);
									return Mono.error(new IllegalStateException("Office is null after mapping response"));
								}
								// map ต่อแบบ non-blocking
								return Mono.just(mapperFacade.map(res, LoginLinkage2.class));
					});
				});
	}

	// -------------------- ยืนยันเข้าใช้งาน --------------------
	@Override
	public Mono<LoginLinkage2Token> confirmLoginLinkage2(ConfirmLoginLinkage2Request request, String departmentCode) {
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
					
					return service.confirmLoginLinkage2(request, ipProxy).flatMap(res -> {
						// ตรวจ null แบบ reactive
						if (res.getToken()== null) {
							log.error("Mapped LoginLinkage2 has null Token: {}", res);
							return Mono.error(new IllegalStateException("Token is null after mapping response"));
						}
						// map ต่อแบบ non-blocking
						return Mono.just(mapperFacade.map(res, LoginLinkage2Token.class));
					});
				});
	}

	// -------------------- ต่ออายุการใช้งาน --------------------
	@Override
	public Mono<LoginLinkage2Token> renewLoginLinkage2(Linkage2TokenRequest request, String departmentCode) {
		// ตรวจสอบค่าเบื้องต้น
		if (!StringUtils.hasText(departmentCode)) {
			log.error("DepartmentCode must not be empty");
			return Mono.error(new IllegalStateException("DepartmentCode must not be empty"));
		}
		
		return findByDepartmentCodeLk2Service(departmentCode)
				.flatMapMany(Flux::fromIterable) // แปลง List → Flux
				.next() // เอาแค่ตัวแรก
				.flatMap(lk2ServiceEntity -> {
					if (!StringUtils.hasText(lk2ServiceEntity.getDepartmentCode())) {
						return Mono.error(new IllegalArgumentException("DepartmentCode not set in lk2Service"));
					}
					// ดึง ipProxy
					String ipProxy = lk2ServiceEntity.getIpProxy();
					// log ดูเพื่อความชัวร์
					log.debug("Using ipProxy [{}] for departmentCode [{}]", ipProxy, departmentCode);
		
					return service.renewLoginLinkage2(request, ipProxy)
							.flatMap(res -> {
								// ตรวจ null แบบ reactive
								if (res.getToken()== null) {
									log.error("Mapped LoginLinkage2 has null Token: {}", res);
									return Mono.error(new IllegalStateException("Token is null after mapping response"));
								}
								// map ต่อแบบ non-blocking
								return Mono.just(mapperFacade.map(res, LoginLinkage2Token.class));
							});
				});
	}

	// -------------------- ออกจากระบบ -------------------- 
	@Override
	public Mono<Void> logoutLinkage2(UsernameRequest request, String departmentCode) {
		
		if (request == null || !StringUtils.hasText(request.getUsername())) {
			log.error("Username must not be empty");
			return Mono.error(new IllegalArgumentException("Username must not be empty"));
		}

		List<DxcUserAccountEntity> userAccountList = userAccountRepository.findByUsername(request.getUsername());
		if (userAccountList.isEmpty()) {
			log.warn("Username {} not found in system", request.getUsername());
			return Mono.error(new IllegalArgumentException("Username is not in the system"));
		}
		log.debug("userAccountList = {}", userAccountList);

		String citizenCardNumber = userAccountList.get(0).getCitizenCardNumber();
		// username ของ Table Lk2TokenService = เลขบัตรประชาชน
		List<Lk2TokenServiceEntity> lk2TokenService = repository.findByUsernameOrderByIdDesc(citizenCardNumber);
		
		// ❗ ถ้ายังไม่เคย login → ไม่ต้อง logout
		if (lk2TokenService.isEmpty()) {
			log.info("User {} has no Linkage2 login history → skip logout", request.getUsername());
			return Mono.empty();
		}
		
		Lk2TokenServiceEntity latestToken = lk2TokenService.get(0);
		
		// ❗ เช็ค channel
		if (!"2".contentEquals(latestToken.getChannel()) && !"9".contentEquals(latestToken.getChannel())) {
			return Mono.error(new IllegalStateException("Channel does not meet the requirements"));
		}
		
		String tokenLk2 = latestToken.getToken();
		
		// ❗ เช็ค expiry token
		try {
			SignedJWT signedJWTKc = SignedJWT.parse(tokenLk2);
//			Number expNum = (Number) signedJWTKc.getJWTClaimsSet().getClaim("exp");
			Date expDate = signedJWTKc.getJWTClaimsSet().getExpirationTime();
			if (expDate == null) {
				log.warn("Token has no exp → skip logout");
				return Mono.empty();
			}
			
			if (expDate.before(new Date())) {
				log.warn("Linkage2 token already expired → skip logout");
				return Mono.empty();
			}
			
//			long expSeconds = expDate.longValue();
//			LocalDateTime expTime = Instant.ofEpochSecond(expSeconds).atZone(ZoneId.systemDefault()).toLocalDateTime();
//			LocalDateTime now = LocalDateTime.now();
//			if (expTime.isBefore(now)) {
//				log.warn("Linkage2 token already expired → skip logout");
//				return Mono.empty();
//			}
		} catch (ParseException e) {
			log.error("Invalid token format", e);
			return Mono.empty();
		}
		
		// ตรวจสอบค่าเบื้องต้น
		if (!StringUtils.hasText(departmentCode)) {
			log.error("DepartmentCode must not be empty");
			return Mono.error(new IllegalStateException("DepartmentCode must not be empty"));
		}
		
		log.debug("tokenLk2 = {}", tokenLk2);
		
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
		
					return service.logoutLinkage2(request, ipProxy, tokenLk2)
							.then();
				});
	}
	
	// -------------------- บันทึก Linkage2 Token -------------------- 
	@Override
	public Mono<Result> saveLinkage2Token(LoginLinkage2Request request, String departmentCode, String sessionKc) {
		if (!StringUtils.hasText(request.getPersonalID()) || "string".equals(request.getPersonalID())) {
			log.error("PersonalID must not be empty");
			return Mono.error(new IllegalStateException("PersonalID must not be empty"));
		}
		
		if (!StringUtils.hasText(request.getLoginType()) || "string".equals(request.getLoginType())) {
			log.error("LoginType must not be empty");
			return Mono.error(new IllegalStateException("LoginType must not be empty"));
		}
		
		if (!"2".equals(request.getLoginType())) {
			log.error("LoginType must be 2");
			return Mono.error(new IllegalStateException("LoginType must be 2"));
		}
		
		if (!StringUtils.hasText(departmentCode)) {
			log.error("DepartmentCode must not be empty");
			return Mono.error(new IllegalStateException("DepartmentCode must not be empty"));
		}
		
		// เรียก loginLinkage2 เพื่อหาข้อมูล office
		return loginLinkage2(request, departmentCode)
				.flatMap(resLoginLk2 -> {
					if (resLoginLk2.getOffice() == null || resLoginLk2.getOffice().isEmpty()) {
						return Mono.error(new IllegalStateException("Office is null or empty"));
					}
					
					// เรียกหา Lk2Service ตาม departmentCode เพื่อหาข้อมูล officeId ของหน่วยงาน
					return findByDepartmentCodeLk2Service(departmentCode)
							.flatMap(lk2Service -> lk2Service.stream().findFirst()
									.map(Mono::just)
									.orElseGet(() -> Mono.error(new IllegalArgumentException("No lk2Service found for departmentCode: " + departmentCode)))
							)
							.flatMap(lk2ServiceEntity -> {
								if (!StringUtils.hasText(lk2ServiceEntity.getDepartmentCode())) {
									return Mono.error(new IllegalArgumentException("DepartmentCode not set in lk2ServiceEntity"));
								}
								// ดึง officeId
								String officeId = lk2ServiceEntity.getOfficeId();
								
								return processOffice(request, officeId, departmentCode, sessionKc);
							});
				});
	}
	
	// -------------------- แยก logic ย่อยออกมาให้อ่านง่าย -------------------- 
	private Mono<Result> processOffice(LoginLinkage2Request request, String officeId, String departmentCode, String sessionKc) {
		return Mono.fromCallable(() -> lk2ThaidLogRepository.findByUsername(request.getPersonalID()))
				.flatMap(lk2ThaidLogList -> Mono.justOrEmpty(lk2ThaidLogList.stream().findFirst()))
				.switchIfEmpty(Mono.error(new IllegalStateException("No linkage2 log found for user")))
				.flatMap(logEntry -> {
					ConfirmLoginLinkage2Request confirmReq = new ConfirmLoginLinkage2Request();
					confirmReq.setLoginType(request.getLoginType());
					confirmReq.setOfficeID(officeId);
					confirmReq.setPersonalID(request.getPersonalID());
					confirmReq.setAccessToken(logEntry.getAccessToken());

					return confirmLoginLinkage2(confirmReq, departmentCode)
							.flatMap(token -> saveNewLinkage2Token(token, sessionKc));
				})
				.subscribeOn(Schedulers.boundedElastic());
	}

	// -------------------- บันทึก token -------------------- 
	private Mono<Result> saveNewLinkage2Token(LoginLinkage2Token token, String sessionKc) {
		try {
			SignedJWT signedJWT = SignedJWT.parse(token.getToken());
			JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
			
			// ดึงค่า userPersonalID จาก token แบบรองรับทุกประเภท
			Object userPersonalIDObj = claims.getClaim("userPersonalID");
			String userPersonalID = (userPersonalIDObj != null) ? userPersonalIDObj.toString() : null;

			// เช่นเดียวกับ loginType
			Object loginTypeObj = claims.getClaim("loginType");
			String loginType = (loginTypeObj != null) ? loginTypeObj.toString() : null; // loginType 2 = login ผ่าน ThaID

			LocalDateTime issuedAt = LocalDateTime.now();

			Lk2TokenService entity = createLk2TokenService(
					token, 
					userPersonalID,
					loginType, 
					issuedAt, 
					sessionKc);

			return Mono.fromCallable(() -> lk2TokenServiceService.insert(entity))
					.thenReturn(new Result("Success"))
					.subscribeOn(Schedulers.boundedElastic());

		} catch (ParseException e) {
			log.error("JWT parsing error", e);
			return Mono.error(new IllegalArgumentException("Invalid token format: " + e));
		}
	}
	
	private Lk2TokenService createLk2TokenService(LoginLinkage2Token res, String username, String loginType, 
			LocalDateTime issuedAt, String sessionKc) {
		Lk2TokenService entity = new Lk2TokenService();
		entity.setUsername(username);
		entity.setToken(res.getToken());
		entity.setInsertTime(issuedAt);
		entity.setChannel(loginType);
		entity.setSessionState(sessionKc);
		entity.setLastActiveTime(LocalDateTime.now());
		return entity;
	}
	
	
	
}
