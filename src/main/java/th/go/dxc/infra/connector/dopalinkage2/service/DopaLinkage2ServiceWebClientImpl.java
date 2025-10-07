package th.go.dxc.infra.connector.dopalinkage2.service;

import java.math.BigInteger;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;
import th.go.dxc.infra.connector.dopalinkage2.config.DopaLinkage2Properties;
import th.go.dxc.infra.connector.dopalinkage2.model.request.ConfirmLoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2RenewRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.response.LoginLinkage2TokenResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.DopaLinkage2ErrorResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.LoginLinkage2Response;
import th.go.dxc.infra.connector.thaid.model.response.TokenErrorResponse;

@Slf4j
public class DopaLinkage2ServiceWebClientImpl implements DopaLinkage2Service {
	
	private static final String  LINKAGE2_LOGIN_PATH = "/api/center/login/"; // login กับ logout ใช้ path เดียวกัน
	private static final String LINKAGE2_LOGIN_CONFIRM = "/api/center/login/confirm";
	private static final String LINKAGE2_LOGIN_RENEW = "/api/center/login/renew";
	private static final String LINKAGE2_USER_JOB = "/api/center/user/job";
	
	private final DopaLinkage2Properties properties;
	private final WebClient webClient;
	
	public DopaLinkage2ServiceWebClientImpl(WebClient.Builder webClientBuilder, DopaLinkage2Properties properties) {
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
				.clientConnector(new ReactorClientHttpConnector(httpClient)).build();
	}

	@Override
	public Mono<LoginLinkage2Response> loginLinkage2(LoginLinkage2Request request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("loginType", parseNumberOrString(request.getLoginType(), Integer::parseInt));
		body.put("personalID", parseNumberOrString(request.getPersonalID(), BigInteger::new));
		
		return postToLinkage2(
				LINKAGE2_LOGIN_PATH, 
				body, 
				LoginLinkage2Response.class, 
				10
				);
	}

	@Override
	public Mono<LoginLinkage2TokenResponse> confirmLoginLinkage2(ConfirmLoginLinkage2Request request) {
		Map<String, Object> body = new HashMap<>();
		body.put("loginType", parseNumberOrString(request.getLoginType(), Integer::parseInt));
		body.put("officeID", parseNumberOrString(request.getOfficeID(), Long::parseLong));
		body.put("personalID", parseNumberOrString(request.getPersonalID(), BigInteger::new));
		body.put("accessToken", request.getAccessToken());
		
		return postToLinkage2(
				LINKAGE2_LOGIN_CONFIRM, 
				body, 
				LoginLinkage2TokenResponse.class, 
				10
				);
	}

	@Override
	public Mono<LoginLinkage2TokenResponse> renewLoginLinkage2(LoginLinkage2RenewRequest request) {
		return webClient.post()
				.uri(LINKAGE2_LOGIN_RENEW)
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + request.getToken())
				.retrieve()
				.onStatus(HttpStatus::isError,
					clientResponse -> clientResponse.bodyToMono(DopaLinkage2ErrorResponse.class)
						.doOnNext(err -> log.error("DOPA Linkage2 error [{}]: {}", err.getErrorNumber(), err.getErrorMessage()))
						.flatMap(errorResponseBody -> Mono.error(new ResponseStatusException(
							clientResponse.statusCode(),
							errorResponseBody.getErrorMessage() != null
								? errorResponseBody.getErrorMessage()
								: "Unknown error from DOPA Linkage2"))))
				.bodyToMono(LoginLinkage2TokenResponse.class)
				.doOnNext(resp -> log.debug("[DOPA Linkage2] Response: {}", resp))
				.timeout(Duration.ofSeconds(10)); // สำหรับ slow request
	}
	
	// -------------------- ส่งคำขอ POST ไปยัง Linkage2 --------------------
	private <T> Mono<T> postToLinkage2(String url, Map<String, Object> body, Class<T> responseClass, Integer timeout) {
		return webClient.post()
				.uri(url)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(body)
				.retrieve()
				.onStatus(HttpStatus::isError,
					clientResponse -> clientResponse.bodyToMono(DopaLinkage2ErrorResponse.class)
						.doOnNext(err -> log.error("DOPA Linkage2 error [{}]: {}", err.getErrorNumber(), err.getErrorMessage()))
						.flatMap(errorResponseBody -> Mono.error(new ResponseStatusException(
							clientResponse.statusCode(),
							errorResponseBody.getErrorMessage() != null
								? errorResponseBody.getErrorMessage()
								: "Unknown error from DOPA Linkage2"))))
				.bodyToMono(responseClass)
				.doOnNext(resp -> log.debug("[DOPA Linkage2] Response: {}", resp))
				.timeout(Duration.ofSeconds(timeout)); // สำหรับ slow request
	}
	
	// -------------------- แปลงค่า Number หรือ String --------------------
	private static <T> Object parseNumberOrString(String value, Function<String, T> parser) {
		if (value == null)
			return null;
		try {
			// ถ้าเป็นตัวเลขที่แปลงได้ เช่น "123"
			return parser.apply(value);
		} catch (NumberFormatException e) {
			// ถ้าไม่ใช่ตัวเลข เช่น "A001" ก็คืนค่า string เดิม
			return value;
		}
	}
	
}
