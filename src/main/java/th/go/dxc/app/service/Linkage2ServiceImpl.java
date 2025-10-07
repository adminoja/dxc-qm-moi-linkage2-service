package th.go.dxc.app.service;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import reactor.core.publisher.Mono;
import th.go.dxc.app.model.LoginLinkage2Token;
import th.go.dxc.app.model.LoginLinkage2;
import th.go.dxc.infra.connector.dopalinkage2.model.request.ConfirmLoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2RenewRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.service.DopaLinkage2Service;

@Slf4j
public class Linkage2ServiceImpl implements Linkage2Service {
	
	private final DopaLinkage2Service service;
	private final MapperFacade mapper;
	
	public Linkage2ServiceImpl(DopaLinkage2Service service, MapperFacade mapper) {
		super();
		this.service = service;
		this.mapper = mapper;
	}

	@Override
	public Mono<LoginLinkage2> loginLinkage2(LoginLinkage2Request request) {
		return service.loginLinkage2(request).flatMap(res -> {
			// ตรวจ null แบบ reactive
			if (res.getOffice() == null) {
				log.error("Mapped LoginLinkage2 has null Office: {}", res);
				return Mono.error(new IllegalStateException("Office is null after mapping response"));
			}
			// map ต่อแบบ non-blocking
			return Mono.just(mapper.map(res, LoginLinkage2.class));
		});
	}

	@Override
	public Mono<LoginLinkage2Token> confirmLoginLinkage2(ConfirmLoginLinkage2Request request) {
		return service.confirmLoginLinkage2(request).flatMap(res -> {
			// ตรวจ null แบบ reactive
			if (res.getToken()== null) {
				log.error("Mapped LoginLinkage2 has null Token: {}", res);
				return Mono.error(new IllegalStateException("Token is null after mapping response"));
			}
			// map ต่อแบบ non-blocking
			return Mono.just(mapper.map(res, LoginLinkage2Token.class));
		});
	}

	@Override
	public Mono<LoginLinkage2Token> renewLoginLinkage2(LoginLinkage2RenewRequest request) {
		return service.renewLoginLinkage2(request).flatMap(res -> {
			// ตรวจ null แบบ reactive
			if (res.getToken()== null) {
				log.error("Mapped LoginLinkage2 has null Token: {}", res);
				return Mono.error(new IllegalStateException("Token is null after mapping response"));
			}
			// map ต่อแบบ non-blocking
			return Mono.just(mapper.map(res, LoginLinkage2Token.class));
		});
	}
	
}
