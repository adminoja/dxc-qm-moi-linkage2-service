package th.go.dxc.infra.connector.thaid.service;

import reactor.core.publisher.Mono;
import th.go.dxc.infra.connector.thaid.model.response.TokenResponse;

public interface ThaidService {

	Mono<TokenResponse> exchangeToken(String code);

}
