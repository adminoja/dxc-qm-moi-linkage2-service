package th.go.dxc.app.service;

import reactor.core.publisher.Mono;
import th.go.dxc.app.model.IntrospectToken;
import th.go.dxc.app.model.Result;
import th.go.dxc.app.model.RevokeToken;
import th.go.dxc.app.model.ThaidToken;
import th.go.dxc.infra.connector.thaid.model.request.AuthorizationCodeRequest;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ThaidLogEntity;

public interface LoginThaidService {

	Mono<ThaidToken> exchangeToken(String code);

	Mono<ThaidToken> freshToken(String refreshToken);

	Mono<IntrospectToken> introspectToken(String accessToken);

	Mono<RevokeToken> revokeToken(String accessToken);

	Mono<Result> saveThaidToken(AuthorizationCodeRequest code, String sessionKc);

	Mono<Lk2ThaidLogEntity> saveThaidTokenReturnData(AuthorizationCodeRequest request, String sessionKc);

	String sessionKeycloakFromToken();
}
