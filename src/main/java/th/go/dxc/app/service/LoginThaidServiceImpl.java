package th.go.dxc.app.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.util.StringUtils;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import th.go.dxc.app.model.IntrospectToken;
import th.go.dxc.app.model.Lk2ThaidLog;
import th.go.dxc.app.model.Result;
import th.go.dxc.app.model.RevokeToken;
import th.go.dxc.app.model.ThaidToken;
import th.go.dxc.infra.connector.thaid.model.request.AuthorizationCodeRequest;
import th.go.dxc.infra.connector.thaid.model.response.TokenResponse;
import th.go.dxc.infra.connector.thaid.service.ThaidService;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ThaidLogEntity;
import th.go.dxc.share.util.mapstruct.MapperFacade;

@Slf4j
public class LoginThaidServiceImpl implements LoginThaidService {

	private final ThaidService service;
	private final MapperFacade mapper;
	private final Lk2ThaidLogService lk2ThaidLogService;

	public LoginThaidServiceImpl(ThaidService service, MapperFacade mapper, Lk2ThaidLogService lk2ThaidLogService) {
		super();
		this.service = service;
		this.mapper = mapper;
		this.lk2ThaidLogService = lk2ThaidLogService;
	}

	// --------------------  ขอ ThaID Token --------------------
	@Override
	public Mono<ThaidToken> exchangeToken(String code) {
		return service.exchangeToken(code).flatMap(res -> {
			// ตรวจ null แบบ reactive
			if (res.getAccessToken() == null) {
				log.error("Mapped ThaidToken has null accessToken: {}", res);
				return Mono.error(new IllegalStateException("Token is null after mapping response"));
			}
			// map ต่อแบบ non-blocking
			return Mono.just(mapper.toThaidToken(res));
		});
	}

	// --------------------  ขอ ThaID Token ใหม่ --------------------
	@Override
	public Mono<ThaidToken> freshToken(String refreshToken) {
		return service.freshToken(refreshToken).flatMap(res -> {
			// ตรวจ null แบบ reactive
			if (res.getAccessToken() == null) {
				log.error("Mapped ThaidToken has null accessToken: {}", res);
				return Mono.error(new IllegalStateException("Token is null after mapping response"));
			}
			// map ต่อแบบ non-blocking
			return Mono.just(mapper.toThaidToken(res));
		});
	}

	// --------------------  ตรวจสอบ ThaID Token --------------------
	@Override
	public Mono<IntrospectToken> introspectToken(String accessToken) {
		return service.introspectToken(accessToken).flatMap(res -> {
			if (res.getActive() == null) {
				log.error("Mapped IntrospectToken has null active: {}", res);
				return Mono.error(new IllegalStateException("Active is null after mapping response"));
			}
			return Mono.just(mapper.toIntrospectToken(res));
		});
	}

	// --------------------  ออกจากระบบ ThaID --------------------
	@Override
	public Mono<RevokeToken> revokeToken(String accessToken) {
		return service.revokeToken(accessToken).flatMap(res -> {
			if (res.getMessage() == null) {
				log.error("Mapped RevokeToken has null message: {}", res);
				return Mono.error(new IllegalStateException("Message is null after mapping response"));
			}
			return Mono.just(mapper.toRevokeToken(res));
		});
	}
	
	// --------------------  บันทึก ThaID Token --------------------
	@Override
	public Mono<Result> saveThaidToken(AuthorizationCodeRequest request, String sessionKc) {
		// ตรวจว่า Code ไม่ว่าง
		if (!StringUtils.hasText(request.getCode()) || "string".equals(request.getCode())) {
			log.error("Code must not be empty");
			return Mono.error(new IllegalStateException("Code must not be empty"));
		}
		return service.exchangeToken(request.getCode())
				.flatMap(resThaid -> validateResThaid(resThaid))
				.flatMap(resThaid -> parseAndSaveLog(resThaid, sessionKc));
	}
	
	// -------------------- บันทึก ThaID Token Return เป็นข้อมูล (ใช้ที่ class LoginThaidAndLinkage2ServiceImpl) --------------------
	@Override
	public Mono<Lk2ThaidLogEntity> saveThaidTokenReturnData(AuthorizationCodeRequest request, String sessionKc) {
		// ตรวจว่า Code ไม่ว่าง
		if (!StringUtils.hasText(request.getCode()) || "string".equals(request.getCode())) {
			log.error("Code must not be empty");
			return Mono.error(new IllegalStateException("Code must not be empty"));
		}
		return service.exchangeToken(request.getCode())
				.flatMap(resThaid -> validateResThaid(resThaid))
				.flatMap(resThaid -> parseAndSaveLogReturnData(resThaid, sessionKc));
	}
	
	// -------------------- ตรวจสอบความถูกต้องของ Response ThaID --------------------
	private Mono<TokenResponse> validateResThaid(TokenResponse resThaid) {
		if (resThaid.getAccessToken() == null) {
			log.error("Token mapping failed: null accessToken");
			return Mono.error(new IllegalStateException("Token is null after mapping response"));
		}
		if (!StringUtils.hasText(resThaid.getIdToken())) {
			return Mono.error(new IllegalStateException("Missing ID Token"));
		}
		return service.validateIdTokenSignature(resThaid.getIdToken())
				.flatMap(isValid -> isValid 
					? Mono.just(resThaid)
					: Mono.error(new SecurityException("Invalid ID Token signature")));
	}
	
	// -------------------- แยกวิเคราะห์และบันทึก --------------------
	private Mono<Result> parseAndSaveLog(TokenResponse resThaid, String sessionKc) {
		try {
			SignedJWT signedJWT = SignedJWT.parse(resThaid.getIdToken());
			JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

			LocalDateTime expires = toLocalDateTime(claims.getExpirationTime().toInstant());
			LocalDateTime issuedAt = toLocalDateTime(claims.getIssueTime().toInstant());

			Lk2ThaidLog logEntity = createLk2ThaidLog(
					resThaid, 
					true, 
					claims.getStringClaim("pid"), 
					claims.getStringClaim("given_name"),
					claims.getStringClaim("family_name"), 
					issuedAt, 
					expires, 
					sessionKc);

			return Mono.fromCallable(() -> lk2ThaidLogService.insert(logEntity))
					.thenReturn(new Result("Success"));
		} catch (ParseException e) {
			return Mono.error(new IllegalArgumentException("Failed to parse ID token: " + e));
		}
	}

	// -------------------- แยกวิเคราะห์และบันทึก Return เป็นข้อมูล  --------------------
	private Mono<Lk2ThaidLogEntity> parseAndSaveLogReturnData(TokenResponse resThaid, String sessionKc) {
		try {
			SignedJWT signedJWT = SignedJWT.parse(resThaid.getIdToken());
			JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

			LocalDateTime expires = toLocalDateTime(claims.getExpirationTime().toInstant());
			LocalDateTime issuedAt = toLocalDateTime(claims.getIssueTime().toInstant());

			Lk2ThaidLog thaidLog = createLk2ThaidLog(
					resThaid, 
					true, 
					claims.getStringClaim("pid"), 
					claims.getStringClaim("given_name"),
					claims.getStringClaim("family_name"), 
					issuedAt, 
					expires, 
					sessionKc);

			return Mono.fromCallable(() -> lk2ThaidLogService.insert(thaidLog))
					.map(saved -> saved); // คืนข้อมูลที่บันทึกจริง
		} catch (ParseException e) {
			return Mono.error(new IllegalArgumentException("Failed to parse ID token: " + e));
		}
	}
		
	private Lk2ThaidLog createLk2ThaidLog(TokenResponse res, Boolean isValid, String pid, String firstname, String lastname, LocalDateTime issuedAt, LocalDateTime expires, String sessionKc) {
		Lk2ThaidLog model = new Lk2ThaidLog();
		model.setAccessToken(res.getAccessToken());
		model.setExpiresIn(expires);
		model.setFirstname(firstname);
		model.setIssuedAt(issuedAt);
		model.setLastname(lastname);
		model.setLoginDatetime(LocalDateTime.now());
		model.setPid(pid);
		model.setRefreshToken(res.getRefreshToken());
		model.setSessionStateKeycloak(sessionKc);
		model.setStatus(isValid);
		model.setType(res.getTokenType());
		model.setUsername(pid);
		return model;
	}
	
	private LocalDateTime toLocalDateTime(Instant instant) {
		return instant.atZone(ZoneId.of("Asia/Bangkok")).toLocalDateTime();
	}
	
}
