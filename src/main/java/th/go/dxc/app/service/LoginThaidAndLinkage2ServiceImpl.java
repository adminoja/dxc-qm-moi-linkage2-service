package th.go.dxc.app.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import th.go.dxc.app.model.Result;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;
import th.go.dxc.infra.connector.thaid.model.request.AuthorizationCodeRequest;
import th.go.dxc.share.security.service.SecurityService;

@Slf4j
public class LoginThaidAndLinkage2ServiceImpl implements LoginThaidAndLinkage2Service {

	private final LoginThaidService loginThaidService;
	private final LoginLinkage2Service loginLinkage2Service;
	
	public LoginThaidAndLinkage2ServiceImpl(LoginThaidService loginThaidService, LoginLinkage2Service loginLinkage2Service) {
		super();
		this.loginThaidService = loginThaidService;
		this.loginLinkage2Service = loginLinkage2Service;
	}

	// -------------------- Login ThaID และ Linkage2 พร้อม บันทึก Token --------------------
	@Override
	public Mono<Result> saveThaidAndLinkage2Token(AuthorizationCodeRequest code, String departmentCode, String sessionKc) {
		return loginThaidService.saveThaidTokenReturnData(code, sessionKc)
				.flatMap(thaidLogEntity -> {
					LoginLinkage2Request request = new LoginLinkage2Request();
					request.setLoginType("2");
					request.setPersonalID(thaidLogEntity.getUsername());
					
					Mono<Result> result = loginLinkage2Service.saveLinkage2Token(request, departmentCode, sessionKc);
					return result;
				});
	}
	
	// -------------------- เรียก session_state จาก Keycloak Token (มี log ออกมาทั้ง Map) --------------------
//	@Override
//	public String sessionKeycloakFromToken() {
//		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
//				.filter(auth -> auth instanceof JwtAuthenticationToken)
//				.map(auth -> (JwtAuthenticationToken) auth)
//				.map(jwtAuth -> {
//					Map<String, Object> attributes = jwtAuth.getTokenAttributes();
//					log.debug("Token attributes: {}", attributes); // 👉 log ออกมาทั้ง Map
//					return String.valueOf(attributes.get("session_state"));
//				})
//				.orElseThrow(() -> new AuthenticationServiceException("กรุณายืนยันตัวตนด้วย ThaID"));
//	}
	// ใช้แบบ reactive
	@Override
	public Mono<String> sessionKeycloakFromToken() {
		return ReactiveSecurityContextHolder.getContext()
				.map(ctx -> ctx.getAuthentication())
				.filter(auth -> auth instanceof JwtAuthenticationToken)
				.map(auth -> {
					Map<String, Object> attributes = ((JwtAuthenticationToken) auth).getTokenAttributes();
					log.debug("Token attributes: {}", attributes);
					return String.valueOf(attributes.get("session_state"));
				}).switchIfEmpty(Mono.error(new AuthenticationServiceException("กรุณายืนยันตัวตนด้วย ThaID")));
	}

	
}
