package th.go.dxc.app.service;

import reactor.core.publisher.Mono;
import th.go.dxc.app.model.LoginLinkage2;
import th.go.dxc.app.model.LoginLinkage2Token;
import th.go.dxc.app.model.Result;
import th.go.dxc.infra.connector.dopalinkage2.model.request.ConfirmLoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.Linkage2TokenRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.UsernameRequest;
import th.go.dxc.share.security.model.DxcUserDetails;

public interface LoginLinkage2Service {

	Mono<LoginLinkage2> loginLinkage2(LoginLinkage2Request request, String departmentCode);

	Mono<LoginLinkage2Token> confirmLoginLinkage2(ConfirmLoginLinkage2Request request, String departmentCode);

	Mono<LoginLinkage2Token> renewLoginLinkage2(Linkage2TokenRequest request, String departmentCode);

	Mono<Void> logoutLinkage2(UsernameRequest request, String departmentCode);

	Mono<Result> saveLinkage2Token(LoginLinkage2Request request, String departmentCode, String sessionKc);

//	String sessionKeycloakFromToken();

	Mono<String> sessionKeycloakFromToken();
}
