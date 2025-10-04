package th.go.dxc.app.service;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import reactor.core.publisher.Mono;
import th.go.dxc.app.model.IntrospectToken;
import th.go.dxc.app.model.RevokeToken;
import th.go.dxc.app.model.ThaidToken;
import th.go.dxc.infra.connector.thaid.model.response.TokenResponse;
import th.go.dxc.infra.connector.thaid.service.ThaidService;

@Slf4j
public class LoginThaidServiceImpl implements LoginThaidService {

	private final ThaidService service;
	private final MapperFacade mapper;

	public LoginThaidServiceImpl(ThaidService service, MapperFacade mapper) {
		super();
		this.service = service;
		this.mapper = mapper;
	}

	@Override
	public Mono<ThaidToken> exchangeToken(String code) {
		return service.exchangeToken(code).flatMap(res -> {
			// ตรวจ null แบบ reactive
			if (res.getAccessToken() == null) {
				log.error("Mapped ThaidToken has null accessToken: {}", res);
				return Mono.error(new IllegalStateException("Token is null after mapping response"));
			}
			// map ต่อแบบ non-blocking
			return Mono.just(mapper.map(res, ThaidToken.class));
		});
	}

	@Override
	public Mono<IntrospectToken> introspectToken(String accessToken) {
		return service.introspectToken(accessToken).flatMap(res -> {
			if (res.getActive() == null) {
				log.error("Mapped IntrospectToken has null active: {}", res);
				return Mono.error(new IllegalStateException("Active is null after mapping response"));
			}
			return Mono.just(mapper.map(res, IntrospectToken.class));
		});
	}

	@Override
	public Mono<RevokeToken> revokeToken(String accessToken) {
		return service.revokeToken(accessToken).flatMap(res -> {
			if (res.getMessage() == null) {
				log.error("Mapped RevokeToken has null message: {}", res);
				return Mono.error(new IllegalStateException("Message is null after mapping response"));
			}
			return Mono.just(mapper.map(res, RevokeToken.class));
		});
	}

}
