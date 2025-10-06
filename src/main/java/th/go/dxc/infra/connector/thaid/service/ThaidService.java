package th.go.dxc.infra.connector.thaid.service;

import reactor.core.publisher.Mono;
import th.go.dxc.infra.connector.thaid.model.response.TokenIntrospectResponse;
import th.go.dxc.infra.connector.thaid.model.response.TokenResponse;
import th.go.dxc.infra.connector.thaid.model.response.TokenRevokeResponse;

public interface ThaidService {

	Mono<TokenResponse> exchangeToken(String code);

	Mono<TokenResponse> freshToken(String freshToken);
	
	Mono<TokenIntrospectResponse> introspectToken(String accessToken);

	Mono<TokenRevokeResponse> revokeToken(String accessToken);

}
