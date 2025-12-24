package th.go.dxc.infra.connector.thaid.service;

import java.text.ParseException;
import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;
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
				.onStatus(HttpStatusCode::isError,
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
				.onStatus(HttpStatusCode::isError,
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
				.onStatus(HttpStatusCode::isError,
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
				.onStatus(HttpStatusCode::isError,
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
				.onStatus(HttpStatusCode::isError,
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
	
	// ====== Token Validation ======
	// เช็คเองเมื่อไม่ใช้ Keycloak
	// -------------------- ตรวจสอบความถูกต้อง idToken ThaiD Signature -------------------- 
	@Override
	public Mono<Boolean> validateIdTokenSignature(String idToken) {
//			{
//			  "alg": "ES256",
//			  "kid": "18ba1879282d4686b47dfe3a2183ec2e",
//			  "typ": "JWT"
//			}
		SignedJWT signedJWT;
		try {
			signedJWT = SignedJWT.parse(idToken);
		} catch (ParseException e) {
			log.error("Failed to parse id_token", e);
			return Mono.just(false);
		}

		// step 1: อ่าน header
		String kidFromToken = signedJWT.getHeader().getKeyID();
		String algFromToken = signedJWT.getHeader().getAlgorithm().getName();
		
		return getJwkSet()
				.flatMap(jwkSet -> {
					// step 2: โหลด JWKS
					// ดึง JWKS จาก ThaiD
					if (jwkSet == null || jwkSet.getKeys().isEmpty()) {
						log.warn("JWKS not found or empty");
						return Mono.just(false);
					}
					// หา JWK ที่ตรงกับ kid
					JWK jwk = jwkSet.getKeyByKeyId(kidFromToken);
					if (jwk == null) {
						log.warn("No matching JWK found for kid: {}", kidFromToken);
						return Mono.just(false);
					}
					// step 3: ตรวจว่า alg ตรงกัน
					String algFromJwk = jwk.getAlgorithm() != null ? jwk.getAlgorithm().getName() : null;
					if (algFromJwk == null || !algFromJwk.equals(algFromToken)) {
						log.warn("Algorithm mismatch: token={}, jwk={}", algFromToken, algFromJwk);
						return Mono.just(false);
					}

					try {
						JWSVerifier verifier;
						if (jwk instanceof ECKey) {
							verifier = new ECDSAVerifier(((ECKey) jwk).toECPublicKey());
						} else if (jwk instanceof RSAKey) {
							verifier = new RSASSAVerifier(((RSAKey) jwk).toRSAPublicKey());
						} else {
							log.warn("Unsupported key type: {}", jwk.getKeyType());
							return Mono.just(false);
						}
						if (!signedJWT.verify(verifier)) {
							log.warn("Invalid signature for ID token");
							return Mono.just(false);
						}
					} catch (Exception e) {
						log.error("Failed to verify signature", e);
						return Mono.just(false);
					}

					JWTClaimsSet claims;
					try {
						claims = signedJWT.getJWTClaimsSet();
						logTokenPayload(claims);
					} catch (ParseException e) {
						log.error("Failed to parse JWT claims", e);
						return Mono.just(false);
					}

					// ตรวจ issuer
					if (!properties.getBaseUrl().equals(claims.getIssuer())) {
						log.warn("Invalid issuer: {}", claims.getIssuer());
						return Mono.just(false);
					}

					// ตรวจ audience
					if (claims.getAudience() == null || !claims.getAudience().contains(properties.getClientId())) {
						log.warn("Invalid audience: {}", claims.getAudience());
						return Mono.just(false);
					}

					// ตรวจ expiration
					if (System.currentTimeMillis() > claims.getExpirationTime().getTime()) {
						log.warn("ID token expired at {}", claims.getExpirationTime());
						return Mono.just(false);
					}
					
					// ตรวจ iat ว่าไม่ห่างจาก expiration เกิน 15 นาที
					long diff = claims.getExpirationTime().getTime() - claims.getIssueTime().getTime();
					if (diff > 15 * 60 * 1000) {
						log.warn("ID token lifetime too long: {} ms", diff);
						return Mono.just(false);
					}

				return Mono.just(true);
		})
		.timeout(Duration.ofSeconds(5)) // เผื่อ JWKS fetch ช้า
		.onErrorResume(ex -> {
			log.error("Error validating id_token signature", ex);
			return Mono.just(false);
		});
		
	}
	
	// --------------------  Log ค่า Payload แบบละเอียด --------------------
	private void logTokenPayload(JWTClaimsSet claims) {
		try {
			log.debug("✅ ID Token Payload:");
			log.debug(" - iss (issuer): {}", claims.getIssuer());
			log.debug(" - aud (audience): {}", claims.getAudience());
			log.debug(" - sub (subject): {}", claims.getSubject());
			log.debug(" - exp (expires): {}", claims.getExpirationTime());
			log.debug(" - iat (issued at): {}", claims.getIssueTime());
			log.debug(" - auth_time: {}", claims.getClaim("auth_time"));
			log.debug(" - pid: {}", claims.getStringClaim("pid"));
			log.debug(" - given_name: {}", claims.getStringClaim("given_name"));
			log.debug(" - family_name: {}", claims.getStringClaim("family_name"));
			log.debug(" - version: {}", claims.getClaim("version"));
			log.debug(" - at_hash: {}", claims.getStringClaim("at_hash"));
		} catch (ParseException e) {
			log.error("❌ Failed to read JWT claim", e);
		}
	}
	
}
