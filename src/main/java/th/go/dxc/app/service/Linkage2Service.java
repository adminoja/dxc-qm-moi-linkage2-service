package th.go.dxc.app.service;

import reactor.core.publisher.Mono;
import th.go.dxc.app.model.ConfirmLoginLinkage2;
import th.go.dxc.app.model.LoginLinkage2;
import th.go.dxc.infra.connector.dopalinkage2.model.request.ConfirmLoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;

public interface Linkage2Service {

	Mono<LoginLinkage2> loginLinkage2(LoginLinkage2Request request);

	Mono<ConfirmLoginLinkage2> confirmLoginLinkage2(ConfirmLoginLinkage2Request request);

}
