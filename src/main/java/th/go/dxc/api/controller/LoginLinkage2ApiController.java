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
import th.go.dxc.app.model.LoginLinkage2;
import th.go.dxc.app.model.ThaidToken;
import th.go.dxc.app.service.Linkage2Service;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;
import th.go.dxc.infra.connector.thaid.model.request.AuthorizationCodeRequest;
import th.go.dxc.share.commons.dto.ErrorDto;

@Tags(value = { @Tag(name = "บริการ Login Linkage2") })
@RestController
@RequestMapping("/api/v2/linkage2")
public class LoginLinkage2ApiController {
	
	private Linkage2Service service;
	
	public LoginLinkage2ApiController(Linkage2Service service) {
		super();
		this.service = service;
	}
	
	@Operation(summary = "ขอเข้าใช้งาน",security = @SecurityRequirement(name="bearerAuth"))
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
	@RequestMapping(method = RequestMethod.POST, value = "/login")
	@ResponseBody
	public Mono<LoginLinkage2> loginLinkage2(@RequestBody LoginLinkage2Request request) {
		return service.loginLinkage2(request);
	}
	
}
