package th.go.dxc.infra.connector.dopalinkage2.service;

import reactor.core.publisher.Mono;
import th.go.dxc.infra.connector.dopalinkage2.model.request.ConfirmLoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.response.ConfirmLoginLinkage2Response;
import th.go.dxc.infra.connector.dopalinkage2.model.response.LoginLinkage2Response;

public interface DopaLinkage2Service {

	Mono<LoginLinkage2Response> loginLinkage2(LoginLinkage2Request request);

	Mono<ConfirmLoginLinkage2Response> confirmLoginLinkage2(ConfirmLoginLinkage2Request request);

}
