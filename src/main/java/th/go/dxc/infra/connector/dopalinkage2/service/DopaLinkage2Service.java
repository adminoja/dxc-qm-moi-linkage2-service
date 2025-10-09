package th.go.dxc.infra.connector.dopalinkage2.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Mono;
import th.go.dxc.app.model.MoiDopaPerson;
import th.go.dxc.infra.connector.dopalinkage2.model.request.ConfirmLoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.Linkage2TokenRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.UsernameRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.response.LoginLinkage2TokenResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse.ResponseItem;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.JobLinkage2Response;
import th.go.dxc.infra.connector.dopalinkage2.model.response.LoginLinkage2Response;

public interface DopaLinkage2Service {

	Mono<LoginLinkage2Response> loginLinkage2(LoginLinkage2Request request);

	Mono<LoginLinkage2TokenResponse> confirmLoginLinkage2(ConfirmLoginLinkage2Request request);

	Mono<LoginLinkage2TokenResponse> renewLoginLinkage2(Linkage2TokenRequest request);

	Mono<Void> logoutLinkage2(UsernameRequest request);

	Mono<JobLinkage2Response> jobLinkage2(Linkage2TokenRequest request);

	<TRequest> Mono<Page<ResponseItem<Object>>> callService(TRequest req, String token, Map<Integer, Class<?>> responseMap);

}
