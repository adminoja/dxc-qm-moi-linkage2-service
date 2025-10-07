package th.go.dxc.app.service;

import reactor.core.publisher.Mono;
import th.go.dxc.app.model.LoginLinkage2Token;
import th.go.dxc.app.model.JobLinkage2;
import th.go.dxc.app.model.LoginLinkage2;
import th.go.dxc.infra.connector.dopalinkage2.model.request.ConfirmLoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.Linkage2TokenRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.UsernameRequest;

public interface Linkage2Service {

	Mono<LoginLinkage2> loginLinkage2(LoginLinkage2Request request);

	Mono<LoginLinkage2Token> confirmLoginLinkage2(ConfirmLoginLinkage2Request request);

	Mono<LoginLinkage2Token> renewLoginLinkage2(Linkage2TokenRequest request);

	Mono<Void> logoutLinkage2(UsernameRequest request);

	Mono<JobLinkage2> jobLinkage2(Linkage2TokenRequest request);

}
