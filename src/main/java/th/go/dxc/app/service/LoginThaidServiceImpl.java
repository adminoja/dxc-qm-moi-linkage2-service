package th.go.dxc.app.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.StringUtils;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import reactor.core.publisher.Mono;
import th.go.dxc.app.model.IntrospectToken;
import th.go.dxc.app.model.Lk2ThaidLog;
import th.go.dxc.app.model.Result;
import th.go.dxc.app.model.RevokeToken;
import th.go.dxc.app.model.ThaidToken;
import th.go.dxc.infra.connector.thaid.model.request.AuthorizationCodeRequest;
import th.go.dxc.infra.connector.thaid.model.response.TokenResponse;
import th.go.dxc.infra.connector.thaid.service.ThaidService;

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
			return Mono.just(mapper.map(res, ThaidToken.class));
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
			return Mono.just(mapper.map(res, ThaidToken.class));
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
			return Mono.just(mapper.map(res, IntrospectToken.class));
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
			return Mono.just(mapper.map(res, RevokeToken.class));
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
		
//				.flatMap(resThaid -> {
//					// ตรวจว่า accessToken ไม่ว่าง
//					if (resThaid.getAccessToken() == null) {
//						log.error("Token mapping failed: null accessToken");
//						return Mono.error(new IllegalStateException("Token is null after mapping response"));
//					}
//
//					// ตรวจว่า idToken มีไหม
//					if (resThaid.getIdToken() == null || resThaid.getIdToken().isEmpty()) {
//						return Mono.error(new IllegalStateException("Missing ID Token"));
//					}
//
//					// ตรวจสอบ signature ของ idToken
//					return service.validateIdTokenSignature(resThaid.getIdToken())
//							.flatMap(isValid -> {
//								if (!isValid) {
//									return Mono.error(new SecurityException("Invalid ID Token signature"));
//								}
//
//								try {
//									// Parse JWT Claims
//									SignedJWT signedJWT = SignedJWT.parse(resThaid.getIdToken());
//									JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
//
//									LocalDateTime expires = toLocalDateTime(claims.getExpirationTime().toInstant());
//									LocalDateTime issuedAt = toLocalDateTime(claims.getIssueTime().toInstant());
//
//									// เก็บ log
//									Lk2ThaidLog logEntity = createLk2ThaidLog(
//											resThaid,
//											isValid, // Boolean ตรงนี้
//											claims.getStringClaim("pid"), 
//											claims.getStringClaim("given_name"),
//											claims.getStringClaim("family_name"), 
//											issuedAt, 
//											expires,
//											sessionKc);
//
//									// insert แบบ synchronous ห่อด้วย Mono
//									return Mono.fromCallable(() -> lk2ThaidLogService.insert(logEntity))
//											.thenReturn(new Result("Success"));
//
//								} catch (ParseException e) {
//									return Mono.error(new IllegalArgumentException("Failed to parse ID token : " + e));
//								}
//							});
//				});
	}
	
	// ตรวจสอบความถูกต้องของ Response ThaID --------------------
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
	
	// แยกวิเคราะห์และบันทึก --------------------
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
	
	// -------------------- ไม่ดู log Keycloak --------------------
//	public String getSidFromToken() {
//	return Optional.of(SecurityContextHolder.getContext().getAuthentication())
//			.filter(auth -> auth instanceof JwtAuthenticationToken)
//			.map(m -> ((JwtAuthenticationToken) m).getTokenAttributes())
//			.map(m -> String.valueOf(m.get("session_state")))
//			.orElseThrow(() -> new AuthenticationServiceException(ErrorConstant.UNAUTHORIZED_MSG));
//}
	
	// -------------------- เอามา log ดู Keycloak --------------------
	@Override
	public String sessionKeycloakFromToken() {
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
				.filter(auth -> auth instanceof JwtAuthenticationToken)
				.map(auth -> (JwtAuthenticationToken) auth)
				.map(jwtAuth -> {
					Map<String, Object> attributes = jwtAuth.getTokenAttributes();
					log.debug("Token attributes: {}", attributes); // 👉 log ออกมาทั้ง Map
					return String.valueOf(attributes.get("session_state"));
				})
				.orElseThrow(() -> new AuthenticationServiceException("กรุณายืนยันตัวตนด้วย ThaID"));
	}

}
