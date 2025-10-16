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
import th.go.dxc.app.model.LoginLinkage2Token;
import th.go.dxc.app.model.Result;
import th.go.dxc.app.model.LoginLinkage2;
import th.go.dxc.app.model.ThaidToken;
import th.go.dxc.app.service.Linkage2Service;
import th.go.dxc.app.service.LoginLinkage2Service;
import th.go.dxc.infra.connector.dopalinkage2.model.request.ConfirmLoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.Linkage2TokenRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.UsernameRequest;
import th.go.dxc.infra.connector.thaid.model.request.AuthorizationCodeRequest;
import th.go.dxc.share.commons.dto.ErrorDto;
import th.go.dxc.share.security.model.DxcUserDetails;
import th.go.dxc.share.security.service.SecurityService;

@Tags(value = { @Tag(name = "บริการ Login Linkage2") })
@RestController
@RequestMapping("/api/moi/linkage2")
public class LoginLinkage2ApiController {
	
	private LoginLinkage2Service service;
	private final SecurityService securityService;
	
	public LoginLinkage2ApiController(LoginLinkage2Service service, SecurityService securityService) {
		super();
		this.service = service;
		this.securityService = securityService;
	}
	
	@Operation(summary = "ขอเข้าใช้งาน",security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = LoginLinkage2.class))),
		
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
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	@ResponseBody
	public Mono<LoginLinkage2> loginLinkage2(@RequestBody LoginLinkage2Request request) {
//		String departmentCode = departmentCode();
//		return service.loginLinkage2(request, departmentCode);
		return securityService.getCurrentUser()
				.flatMap(currentUser -> service
						.loginLinkage2(request, currentUser.getUserOrganizationId()));
	}
	
	@Operation(summary = "ยืนยันเข้าใช้งาน",security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = LoginLinkage2Token.class))),
		
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
	@RequestMapping(method = RequestMethod.POST, value = "/login/confirm")
	@ResponseBody
	public Mono<LoginLinkage2Token> confirmLoginLinkage2(@RequestBody ConfirmLoginLinkage2Request request) {
//		String departmentCode = departmentCode();
//		return service.confirmLoginLinkage2(request, departmentCode);
		return securityService.getCurrentUser()
				.flatMap(currentUser -> service
						.confirmLoginLinkage2(request, currentUser.getUserOrganizationId()));
	}
	
	@Operation(summary = "ต่ออายุการใช้งาน",security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = LoginLinkage2Token.class))),
		
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
	@RequestMapping(method = RequestMethod.POST, value = "/login/renew")
	@ResponseBody
	public Mono<LoginLinkage2Token> renewLoginLinkage2(@RequestBody Linkage2TokenRequest request) {
//		String departmentCode = departmentCode();
//		return service.renewLoginLinkage2(request, departmentCode);
		return securityService.getCurrentUser()
				.flatMap(currentUser -> service
						.renewLoginLinkage2(request, currentUser.getUserOrganizationId()));
	}
	
	@Operation(summary = "ออกจากระบบ",security = @SecurityRequirement(name="bearerAuth"))
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
//	@RequestMapping(method = RequestMethod.DELETE, value = "/logout")
	@ResponseBody
	public Mono<Void> logoutLinkage2(@RequestBody UsernameRequest request) {
//		String departmentCode = departmentCode();
//		return service.logoutLinkage2(request, departmentCode);
		return securityService.getCurrentUser()
				.flatMap(currentUser -> service
						.logoutLinkage2(request, currentUser.getUserOrganizationId()));
	}
	
	@Operation(summary = "บันทึก Linkage2 Token",security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = Result.class))),
		
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
	@RequestMapping(method = RequestMethod.POST, value = "/login/inset")
	@ResponseBody
	public Mono<Result> saveLinkage2Token(@RequestBody LoginLinkage2Request request) {
//		String departmentCode = departmentCode();
//		String sessionKc = service.sessionKeycloakFromToken();
//		return service.saveLinkage2Token(request, departmentCode, sessionKc);
		
		// ใช้แบบ reactive
//		return securityService.getCurrentUser()
//				.flatMap(currentUser -> service.sessionKeycloakFromToken().flatMap(sessionKc -> service
//						.saveLinkage2Token(request, currentUser.getUserOrganizationId(), sessionKc)));
//		String sessionKc = ""; // ใส่ไปก่อนไม่รู้จำเป็นไหม
		return securityService.getCurrentUser()
				.flatMap(currentUser -> service.saveLinkage2Token(request, currentUser.getUserOrganizationId(), currentUser.getSessionState()));
	}
	
//	public String departmentCode() {
//		DxcUserDetails currentUser = securityService.getCurrentUser();
//		String departmentCode = currentUser.getUserOrganizationId();
//		return departmentCode;
//	}
	
}
