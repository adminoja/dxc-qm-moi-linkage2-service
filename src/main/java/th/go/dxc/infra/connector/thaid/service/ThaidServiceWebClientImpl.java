package th.go.dxc.infra.connector.thaid.service;

import java.time.Duration;
import java.util.List;

import org.hibernate.event.internal.ReattachVisitor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.nimbusds.jose.jwk.JWKSet;

import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;
import th.go.dxc.app.model.IntrospectToken;
import th.go.dxc.infra.connector.thaid.config.ThaidProperties;
import th.go.dxc.infra.connector.thaid.model.response.TokenErrorResponse;
import th.go.dxc.infra.connector.thaid.model.response.TokenIntrospectResponse;
import th.go.dxc.infra.connector.thaid.model.response.TokenResponse;
import th.go.dxc.infra.connector.thaid.model.response.TokenRevokeResponse;

@Slf4j
public class ThaidServiceWebClientImpl implements ThaidService {

	private static final String THAID_TOKEN_PATH = "/api/v2/oauth2/token/";
	private static final String THAID_INTROSPECT_PATH = "/api/v2/oauth2/introspect/";
	private static final String THAID_REVOKE_PATH = "/api/v2/oauth2/revoke/";
	private static final String THAID_JWKS_PATH = "/jwks/";
	
	private final WebClient webClient;
	private final ThaidProperties properties;

	public ThaidServiceWebClientImpl(WebClient.Builder webClientBuilder, ThaidProperties properties) {
		super();
		this.properties = properties;

		HttpClient httpClient;
		if (properties.isEnableWiretap()) {
			// Dev: log headers + body
			httpClient = HttpClient.create().wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
		} else {
			// Prod: log headers/status only
			httpClient = HttpClient.create()
				.wiretap(true);
		}
		this.webClient = webClientBuilder.baseUrl(properties.getBaseUrl())
				.defaultHeader(HttpHeaders.AUTHORIZATION, properties.getAuthorization()) // Basic xxxxx
				.clientConnector(new ReactorClientHttpConnector(httpClient)).build();
		
//		this.getJwkSet().subscribe(
//				jwkSet -> log.info("JWKS keys loaded: {}", jwkSet.getKeys().get(0)),
//				error -> log.error("Failed to load JWKS", error));
	}

	@Override
	public Mono<TokenResponse> exchangeToken(String code) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("code", code);
		formData.add("grant_type", properties.getAuthorizationCode());
		formData.add("redirect_uri", properties.getRedirectUri());

		return webClient.post()
				.uri(THAID_TOKEN_PATH)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(BodyInserters.fromFormData(formData))
				.retrieve()
				.onStatus(HttpStatus::isError,
						clientResponse -> clientResponse.bodyToMono(TokenErrorResponse.class)
								.doOnNext(err -> log.error("Error from ThaID: {}", err)) // ✅ log error
								.flatMap(errorResponseBody -> Mono.error(new ResponseStatusException(
										clientResponse.statusCode(),
										errorResponseBody.getErrorDescription() != null
												? errorResponseBody.getErrorDescription()
												: (errorResponseBody.getError() != null ? errorResponseBody.getError()
														: "Unknown error from ThaID")))))
				.bodyToMono(TokenResponse.class)
				.timeout(Duration.ofSeconds(10)); // สำหรับ slow request
	}

	@Override
	public Mono<TokenResponse> freshToken(String freshToken) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("refresh_token", freshToken);
		formData.add("grant_type", properties.getRefreshToken());
		formData.add("redirect_uri", properties.getRedirectUri());

		return webClient.post()
				.uri(THAID_TOKEN_PATH)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(BodyInserters.fromFormData(formData))
				.retrieve()
				.onStatus(HttpStatus::isError,
						clientResponse -> clientResponse.bodyToMono(TokenErrorResponse.class)
								.doOnNext(err -> log.error("Error from ThaID: {}", err)) // ✅ log error
								.flatMap(errorResponseBody -> Mono.error(new ResponseStatusException(
										clientResponse.statusCode(),
										errorResponseBody.getErrorDescription() != null
												? errorResponseBody.getErrorDescription()
												: (errorResponseBody.getError() != null ? errorResponseBody.getError()
														: "Unknown error from ThaID")))))
				.bodyToMono(TokenResponse.class)
				.timeout(Duration.ofSeconds(10)); // สำหรับ slow request
	}

	@Override
	public Mono<TokenIntrospectResponse> introspectToken(String accessToken) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("token", accessToken);
		
		return webClient.post()
				.uri(THAID_INTROSPECT_PATH)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(BodyInserters.fromFormData(formData))
				.retrieve()
				.onStatus(HttpStatus::isError,
						clientResponse -> clientResponse.bodyToMono(TokenErrorResponse.class)
								.doOnNext(err -> log.error("Error from ThaID: {}", err)) // ✅ log error
								.flatMap(errorResponseBody -> Mono.error(new ResponseStatusException(
										clientResponse.statusCode(),
										errorResponseBody.getErrorDescription() != null
												? errorResponseBody.getErrorDescription()
												: (errorResponseBody.getError() != null ? errorResponseBody.getError()
														: "Unknown error from ThaID")))))
				.bodyToMono(TokenIntrospectResponse.class)
				.timeout(Duration.ofSeconds(10)); // สำหรับ slow request
	}

	@Override
	public Mono<TokenRevokeResponse> revokeToken(String accessToken) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add("token", accessToken);
		
		return webClient.post()
				.uri(THAID_REVOKE_PATH)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(BodyInserters.fromFormData(formData))
				.retrieve()
				.onStatus(HttpStatus::isError,
						clientResponse -> clientResponse.bodyToMono(TokenErrorResponse.class)
								.doOnNext(err -> log.error("Error from ThaID: {}", err)) // ✅ log error
								.flatMap(errorResponseBody -> Mono.error(new ResponseStatusException(
										clientResponse.statusCode(),
										errorResponseBody.getErrorDescription() != null
												? errorResponseBody.getErrorDescription()
												: (errorResponseBody.getError() != null ? errorResponseBody.getError()
														: "Unknown error from ThaID")))))
				.bodyToMono(TokenRevokeResponse.class)
				.timeout(Duration.ofSeconds(10)); // สำหรับ slow request
	}
	
	// -------------------- เช็ค JWKS -------------------- 
	public Mono<JWKSet> getJwkSet() {
		return webClient.get()
				.uri(THAID_JWKS_PATH)
				.retrieve()
				.onStatus(HttpStatus::isError,
						clientResponse -> clientResponse.bodyToMono(String.class)
							.flatMap(errorBody -> {
								log.error("❌ Failed to fetch JWKS. Response: {}", errorBody);
								return Mono.error(new ResponseStatusException(clientResponse.statusCode(),
										"Failed to fetch JWKS from ThaID"));
				}))
				.bodyToMono(String.class)
				.flatMap(jwksJson -> {
					try {
						JWKSet jwkSet = JWKSet.parse(jwksJson);
						log.info("✅ Successfully fetched JWKS ({} keys)", jwkSet.getKeys().size());
						return Mono.just(jwkSet);
					} catch (Exception e) {
						log.error("❌ Failed to parse JWKS JSON", e);
						return Mono.error(new IllegalStateException("Invalid JWKS JSON format", e));
					}
				})
				.timeout(Duration.ofSeconds(5)); // เผื่อ timeout ป้องกันค้างนานเกินไป
	}
	
	
}
