package th.go.dxc.infra.connector.dopalinkage2.service;

import java.math.BigInteger;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import io.netty.handler.logging.LogLevel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;
import th.go.dxc.infra.connector.dopalinkage2.config.DopaLinkage2Properties;
import th.go.dxc.infra.connector.dopalinkage2.model.request.ConfirmLoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.Linkage2TokenRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.PersonProfileRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.UsernameRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.response.LoginLinkage2TokenResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.DopaLinkage2ErrorResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse.ResponseItem;
import th.go.dxc.infra.connector.dopalinkage2.model.response.JobLinkage2Response;
import th.go.dxc.infra.connector.dopalinkage2.model.response.LoginLinkage2Response;
import th.go.dxc.infra.connector.thaid.model.response.TokenErrorResponse;

@Slf4j
public class DopaLinkage2ServiceWebClientImpl implements DopaLinkage2Service {
	
	private static final String  LINKAGE2_LOGIN_PATH = "/api/center/login/"; // login กับ logout ใช้ path เดียวกัน
	private static final String LINKAGE2_LOGIN_CONFIRM_PATH = "/api/center/login/confirm";
	private static final String LINKAGE2_LOGIN_RENEW_PATH = "/api/center/login/renew";
	private static final String LINKAGE2_USER_JOB_PATH = "/api/center/user/job";
	private static final String LINKAGE2_REQUEST_PATH = "/api/center/request/";
	
	private final DopaLinkage2Properties properties;
	private final WebClient.Builder webClientBuilder;  // ✅ เพิ่มตัวนี้
	private final HttpClient httpClient; // ✅ เพิ่มตัวนี้
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	public DopaLinkage2ServiceWebClientImpl(WebClient.Builder webClientBuilder, DopaLinkage2Properties properties) {
		super();
		this.properties = properties;
		this.webClientBuilder = webClientBuilder; // ✅ เก็บ builder ไว้ใช้อีกที
		try {
			// สร้าง SSL context แบบไม่ตรวจสอบใบรับรอง
			SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)
					.build();

			HttpClient baseClient = HttpClient.create().secure(spec -> spec.sslContext(sslContext));

			if (properties.isEnableWiretap()) {
				this.httpClient = baseClient.wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG,
						AdvancedByteBufFormat.TEXTUAL);
			} else {
				this.httpClient = baseClient.wiretap(true);
			}

		} catch (Exception e) {
			throw new RuntimeException("Error initializing SSL context", e);
		}
	}
	
	private WebClient buildClient(String ipProxy) {
		return webClientBuilder
				.baseUrl(ipProxy)
				.clientConnector(new ReactorClientHttpConnector(httpClient))
				.build();
	}

	@Override
	public Mono<LoginLinkage2Response> loginLinkage2(LoginLinkage2Request request, String ipProxy) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("loginType", parseNumberOrString(request.getLoginType(), Integer::parseInt));
		body.put("personalID", parseNumberOrString(request.getPersonalID(), BigInteger::new));
		
		return postToLinkage2(
				LINKAGE2_LOGIN_PATH, 
				body, 
				LoginLinkage2Response.class, 
				10,
				ipProxy
				);
	}

	@Override
	public Mono<LoginLinkage2TokenResponse> confirmLoginLinkage2(ConfirmLoginLinkage2Request request, String ipProxy) {
		Map<String, Object> body = new HashMap<>();
		body.put("loginType", parseNumberOrString(request.getLoginType(), Integer::parseInt));
		body.put("officeID", parseNumberOrString(request.getOfficeID(), Long::parseLong));
		body.put("personalID", parseNumberOrString(request.getPersonalID(), BigInteger::new));
		body.put("accessToken", request.getAccessToken());
		
		return postToLinkage2(
				LINKAGE2_LOGIN_CONFIRM_PATH, 
				body, 
				LoginLinkage2TokenResponse.class, 
				10,
				ipProxy
				);
	}

	@Override
	public Mono<LoginLinkage2TokenResponse> renewLoginLinkage2(Linkage2TokenRequest request, String ipProxy) {
		WebClient webClient = buildClient(ipProxy);
		
		return webClient.post()
				.uri(LINKAGE2_LOGIN_RENEW_PATH)
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + request.getToken())
				.retrieve()
				.onStatus(HttpStatus::isError,
					clientResponse -> clientResponse.bodyToMono(DopaLinkage2ErrorResponse.class)
						.doOnNext(err -> log.error("DOPA Linkage2 Renew error [{}]: {}", err.getErrorNumber(), err.getErrorMessage()))
						.flatMap(errorResponseBody -> Mono.error(new ResponseStatusException(
							clientResponse.statusCode(),
							errorResponseBody.getErrorMessage() != null
								? errorResponseBody.getErrorMessage()
								: "Renew unknown error from DOPA Linkage2"))))
				.bodyToMono(LoginLinkage2TokenResponse.class)
				.doOnNext(resp -> log.debug("[DOPA Linkage2] Renew Response: {}", resp))
				.timeout(Duration.ofSeconds(10)); // สำหรับ slow request
	}

	@Override
	public Mono<Void> logoutLinkage2(UsernameRequest request, String ipProxy, String tokenLk2) {
		WebClient webClient = buildClient(ipProxy);
		
		// ตรวจสอบ Usrename
		if (request == null || !StringUtils.hasText(request.getUsername()) || "string".equalsIgnoreCase(request.getUsername())) {
			throw new IllegalArgumentException("Invalid username.");
		}
		
		return webClient.delete()
				.uri(LINKAGE2_LOGIN_PATH)
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenLk2)
				.retrieve()
				.onStatus(HttpStatus::isError,
					clientResponse -> clientResponse.bodyToMono(DopaLinkage2ErrorResponse.class)
						.doOnNext(err -> log.error("DOPA Linkage2 error [{}]: {}", err.getErrorNumber(), err.getErrorMessage()))
						.flatMap(errorResponseBody -> Mono.error(new ResponseStatusException(
							clientResponse.statusCode(),
							errorResponseBody.getErrorMessage() != null
								? errorResponseBody.getErrorMessage()
								: "Unknown error from DOPA Linkage2"))))
				.toBodilessEntity() // ไม่แสดง body
				.doOnNext(resp -> log.debug("[DOPA Linkage2] Response: {}", resp))
				.then() // แปลงเป็น Mono<Void>
				.timeout(Duration.ofSeconds(10)); // สำหรับ slow request
	}

	@Override
	public Mono<JobLinkage2Response> jobLinkage2(Linkage2TokenRequest request, String ipProxy) {
		WebClient webClient = buildClient(ipProxy);
		
		return webClient.get()
				.uri(LINKAGE2_USER_JOB_PATH)
				.accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + request.getToken())
				.retrieve()
				.onStatus(HttpStatus::isError,
					clientResponse -> clientResponse.bodyToMono(DopaLinkage2ErrorResponse.class)
						.doOnNext(err -> log.error("DOPA Linkage2 Job error [{}]: {}", err.getErrorNumber(), err.getErrorMessage()))
						.flatMap(errorResponseBody -> Mono.error(new ResponseStatusException(
							clientResponse.statusCode(),
							errorResponseBody.getErrorMessage() != null
								? errorResponseBody.getErrorMessage()
								: "Job unknown error from DOPA Linkage2"))))
				.bodyToMono(JobLinkage2Response.class)
				.doOnNext(resp -> log.debug("[DOPA Linkage2] Job Response: {}", resp))
				.timeout(Duration.ofSeconds(10)); // สำหรับ slow request
	}
	
	// -------------------- ส่งคำขอ POST ไปยัง Linkage2 --------------------
	private <T> Mono<T> postToLinkage2(String url, Map<String, Object> body, Class<T> responseClass, Integer timeout, String ipProxy) {
		WebClient webClient = buildClient(ipProxy);
		
		return webClient.post()
				.uri(url)
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(body)
				.retrieve()
				.onStatus(HttpStatus::isError,
					clientResponse -> clientResponse.bodyToMono(DopaLinkage2ErrorResponse.class)
						.doOnNext(err -> log.error("DOPA Linkage2 Login error [{}]: {}", err.getErrorNumber(), err.getErrorMessage()))
						.flatMap(errorResponseBody -> Mono.error(new ResponseStatusException(
							clientResponse.statusCode(),
							errorResponseBody.getErrorMessage() != null
								? errorResponseBody.getErrorMessage()
								: "Login unknown error from DOPA Linkage2"))))
				.bodyToMono(responseClass)
				.doOnNext(resp -> log.debug("[DOPA Linkage2] Login Response: {}", resp))
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

	// -------------------- Generic callService method -------------------- 
	@Override
	public <TRequest> Mono<Page<GenericResponse.ResponseItem<Object>>> callService(TRequest req, String token, Map<Integer, Class<?>> responseMap, String ipProxy) {
		WebClient webClient = buildClient(ipProxy);
		
		// เรียก WebClient
		return webClient.post()
			.uri(LINKAGE2_REQUEST_PATH)
			.header("Authorization", "Bearer " + token)
			.bodyValue(req)
			.retrieve()
			.onStatus(HttpStatus::isError,
					clientResponse -> clientResponse.bodyToMono(DopaLinkage2ErrorResponse.class)
						.doOnNext(err -> log.error("DOPA Linkage2 Request error [{}]: {}", err.getErrorNumber(), err.getErrorMessage()))
						.flatMap(errorResponseBody -> Mono.error(new ResponseStatusException(
							clientResponse.statusCode(),
							errorResponseBody.getErrorMessage() != null
								? errorResponseBody.getErrorMessage()
								: "Request unknown error from DOPA Linkage2"))))
			.bodyToMono(new ParameterizedTypeReference<GenericResponse<Object>>() {})
//			.block();
			.map(response -> {	
		//		// map responseData เป็น Object ตาม serviceID
				List<GenericResponse.ResponseItem<Object>> items = response.getData().stream()
						.map(item -> {
							Object rawData = item.getResponseData();
							Object data;
//							if (item.getResponseStatus() != 200 || item.getResponseData() instanceof Map) {
//								// ถ้าไม่สำเร็จ หรือ response เป็นข้อความ/404 → เก็บ JsonNode ดิบ
//								data = objectMapper.convertValue(item.getResponseData(), JsonNode.class);
//							} else {
//		//						// ถ้า 200 → map เป็น class ของ service
//								Class<?> clazz = responseMap.getOrDefault(item.getServiceID(), JsonNode.class);
//		//						
//								if (rawData instanceof List) {
//									// ถ้า responseData เป็น array → map เป็น List ของ clazz
//									CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class,
//											clazz);
//									data = objectMapper.convertValue(rawData, listType);
//								} else if (rawData instanceof Map) {
//									// ถ้าเป็น object เดี่ยว → map เป็น clazz
//									data = objectMapper.convertValue(rawData, clazz);
//								} else {
//									// fallback → เก็บ rawData ดิบ
//									data = rawData;
//								}
//							}
							
							// ดึง class จาก responseMap ตาม serviceID
								Class<?> clazz = responseMap.getOrDefault(item.getServiceID(), Object.class);

								if (rawData instanceof List) {
									// ถ้าเป็น array → map เป็น List ของ clazz
									CollectionType listType = objectMapper.getTypeFactory()
											.constructCollectionType(List.class, clazz);
									data = objectMapper.convertValue(rawData, listType);
								} else if (rawData instanceof Map
										|| rawData instanceof com.fasterxml.jackson.databind.JsonNode) {
									// ถ้าเป็น Map หรือ ObjectNode → map เป็น clazz
									data = objectMapper.convertValue(rawData, clazz);
								} else {
									// fallback → เก็บ rawData ดิบ
									data = rawData;
								}
							
							GenericResponse.ResponseItem<Object> newItem = new GenericResponse.ResponseItem<>();
							newItem.setServiceID(item.getServiceID());
							newItem.setResponseData(data);
							newItem.setResponseStatus(item.getResponseStatus());
							newItem.setResponseError(item.getResponseError());
							newItem.setResponseTimeMs(item.getResponseTimeMs());
							return newItem;
						}).collect(Collectors.toList());
		
				return new PageImpl<>(items);
		
		});
	}
	
	
}
