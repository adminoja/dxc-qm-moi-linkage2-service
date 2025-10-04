package th.go.dxc.api.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import reactor.core.publisher.Mono;
import th.go.dxc.app.model.IntrospectToken;
import th.go.dxc.app.model.RevokeToken;
import th.go.dxc.app.model.ThaidToken;
import th.go.dxc.app.service.LoginThaidService;
import th.go.dxc.infra.connector.thaid.model.request.TokenRequest;
import th.go.dxc.infra.connector.thaid.model.request.AuthorizationCodeRequest;
import th.go.dxc.share.commons.dto.ErrorDto;

@Tags(value = { @Tag(name = "บริการ Login ThaID") })
@RestController
@RequestMapping("/api/v2/login-thaid/token")
public class LoginThaidApiController {
	
	private final LoginThaidService service;
	
	public LoginThaidApiController(LoginThaidService service) {
		super();
		this.service = service;
	}
	
	@Operation(summary = "บริการขอ Token ThaID",security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"),
		
		@ApiResponse(responseCode = "400",description = "เรียกใช้งานไม่ถูกต้อง"
		,content = @Content(mediaType = "application/json"
		, schema = @Schema(implementation = ErrorDto.class))),

		@ApiResponse(responseCode = "401",description = "การยืนยันตัวตนไม่ถูกต้อง Token หรือ รหัสยืนยันตัวตนมีปัญหา"
		,headers = {@Header(name = "www-authenticate",description = "รายละเอียดข้อผิดพลาด (ถ้ามี)")}
		,content = @Content(schema = @Schema(hidden=true))),

		@ApiResponse(responseCode = "403",description = "ไม่มีสิทธิในการใช้บริการ"
		,content = @Content(mediaType = "application/json"
		, schema = @Schema(implementation = ErrorDto.class))),
		
		@ApiResponse(responseCode = "500",description = "ระบบทำงานผิดพลาด กรุณาติดต่อผู้ดูแลระบบ"
		,content = @Content(mediaType = "application/json"
		, schema = @Schema(implementation = ErrorDto.class)))
	})
	@RequestMapping(method = RequestMethod.POST, value = "")
	@ResponseBody
	public Mono<ThaidToken> exchangeToken(@RequestBody AuthorizationCodeRequest request) {
		return service.exchangeToken(request.getCode());
	}
	
	@Operation(summary = "ตรวจสอบ AccessToken ThaID",security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"),
		
		@ApiResponse(responseCode = "400",description = "เรียกใช้งานไม่ถูกต้อง"
		,content = @Content(mediaType = "application/json"
		, schema = @Schema(implementation = ErrorDto.class))),

		@ApiResponse(responseCode = "401",description = "การยืนยันตัวตนไม่ถูกต้อง Token หรือ รหัสยืนยันตัวตนมีปัญหา"
		,headers = {@Header(name = "www-authenticate",description = "รายละเอียดข้อผิดพลาด (ถ้ามี)")}
		,content = @Content(schema = @Schema(hidden=true))),

		@ApiResponse(responseCode = "403",description = "ไม่มีสิทธิในการใช้บริการ"
		,content = @Content(mediaType = "application/json"
		, schema = @Schema(implementation = ErrorDto.class))),
		
		@ApiResponse(responseCode = "500",description = "ระบบทำงานผิดพลาด กรุณาติดต่อผู้ดูแลระบบ"
		,content = @Content(mediaType = "application/json"
		, schema = @Schema(implementation = ErrorDto.class)))
	})
	@RequestMapping(method = RequestMethod.POST, value = "/introspect")
	@ResponseBody
	public Mono<IntrospectToken> introspectToken(@RequestBody TokenRequest request) {
		return service.introspectToken(request.getAccessToken());
	}
	
	@Operation(summary = "ออกจากระบบ ThaID",security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"),
		
		@ApiResponse(responseCode = "400",description = "เรียกใช้งานไม่ถูกต้อง"
		,content = @Content(mediaType = "application/json"
		, schema = @Schema(implementation = ErrorDto.class))),

		@ApiResponse(responseCode = "401",description = "การยืนยันตัวตนไม่ถูกต้อง Token หรือ รหัสยืนยันตัวตนมีปัญหา"
		,headers = {@Header(name = "www-authenticate",description = "รายละเอียดข้อผิดพลาด (ถ้ามี)")}
		,content = @Content(schema = @Schema(hidden=true))),

		@ApiResponse(responseCode = "403",description = "ไม่มีสิทธิในการใช้บริการ"
		,content = @Content(mediaType = "application/json"
		, schema = @Schema(implementation = ErrorDto.class))),
		
		@ApiResponse(responseCode = "500",description = "ระบบทำงานผิดพลาด กรุณาติดต่อผู้ดูแลระบบ"
		,content = @Content(mediaType = "application/json"
		, schema = @Schema(implementation = ErrorDto.class)))
	})
	@RequestMapping(method = RequestMethod.POST, value = "/revoke")
	@ResponseBody
	public Mono<RevokeToken> revokeToken(@RequestBody TokenRequest request) {
		return service.revokeToken(request.getAccessToken());
	}
	
}
