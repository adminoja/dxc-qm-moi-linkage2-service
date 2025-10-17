package th.go.dxc.app.service;

import reactor.core.publisher.Mono;
import th.go.dxc.app.model.Result;
import th.go.dxc.infra.connector.thaid.model.request.AuthorizationCodeRequest;

public interface LoginThaidAndLinkage2Service {
	
	Mono<Result> saveThaidAndLinkage2Token(AuthorizationCodeRequest code, String departmentCode, String sessionKc);

}
