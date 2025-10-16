package th.go.dxc.app.service;

import reactor.core.publisher.Mono;
import th.go.dxc.app.model.Result;
import th.go.dxc.infra.connector.thaid.model.request.AuthorizationCodeRequest;
import th.go.dxc.share.security.model.DxcUserDetails;

public interface LoginThaidAndLinkage2Service {

//	String sessionKeycloakFromToken();
	
	Mono<String> sessionKeycloakFromToken();
	
	Mono<Result> saveThaidAndLinkage2Token(AuthorizationCodeRequest code, String departmentCode, String sessionKc);


}
