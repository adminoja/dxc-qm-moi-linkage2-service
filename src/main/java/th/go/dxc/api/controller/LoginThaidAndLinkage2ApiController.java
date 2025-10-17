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
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import th.go.dxc.app.model.Result;
import th.go.dxc.app.service.LoginThaidAndLinkage2Service;
import th.go.dxc.infra.connector.thaid.model.request.AuthorizationCodeRequest;
import th.go.dxc.share.commons.dto.ErrorDto;
import th.go.dxc.share.security.service.SecurityService;

@Slf4j
@Tags(value = { @Tag(name = "บริการ Login ThaID และ Linkage2") })
@RestController
@RequestMapping("/api/login/thaid-linkage2")
public class LoginThaidAndLinkage2ApiController {
	
	private final LoginThaidAndLinkage2Service service;
	private final SecurityService securityService;
	
	public LoginThaidAndLinkage2ApiController(LoginThaidAndLinkage2Service service, SecurityService securityService) {
		super();
		this.service = service;
		this.securityService = securityService;
	}
	
	@Operation(summary = "บันทึก ThaID Token และ Linkage2 Token",security = @SecurityRequirement(name="bearerAuth"))
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
	@RequestMapping(method = RequestMethod.POST, value = "/inset")
	@ResponseBody
	public Mono<Result> saveThaidAndLinkage2Token(@RequestBody AuthorizationCodeRequest code) {
		return securityService.getCurrentUser()
				.flatMap(currentUser -> service.saveThaidAndLinkage2Token(code, currentUser.getUserOrganizationId(), currentUser.getSessionState()));
	}
	
}
