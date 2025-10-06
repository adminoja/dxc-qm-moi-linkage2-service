package th.go.dxc.app.service;

import reactor.core.publisher.Mono;
import th.go.dxc.app.model.IntrospectToken;
import th.go.dxc.app.model.RevokeToken;
import th.go.dxc.app.model.ThaidToken;

public interface LoginThaidService {

	Mono<ThaidToken> exchangeToken(String code);

	Mono<ThaidToken> freshToken(String refreshToken);

	Mono<IntrospectToken> introspectToken(String accessToken);

	Mono<RevokeToken> revokeToken(String accessToken);
}
