package th.go.dxc.app.service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import th.go.dxc.app.model.Result;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;
import th.go.dxc.infra.connector.thaid.model.request.AuthorizationCodeRequest;

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
	
}
