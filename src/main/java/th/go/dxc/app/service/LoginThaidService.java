package th.go.dxc.app.service;

import reactor.core.publisher.Mono;
import th.go.dxc.app.model.ThaidToken;

public interface LoginThaidService {

	Mono<ThaidToken> exchangeToken(String code);

}
