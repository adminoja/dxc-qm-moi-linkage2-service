package th.go.dxc.app.service;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import reactor.core.publisher.Mono;
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
		return service.exchangeToken(code).map(res -> {
			if (res.getAccessToken() == null) {
				log.error("Mapped ThaidToken has null accessToken: {}", res);
				throw new IllegalStateException("Token is null after mapping response");
			}
			return mapper.map(res, ThaidToken.class);
		});
	}

}
