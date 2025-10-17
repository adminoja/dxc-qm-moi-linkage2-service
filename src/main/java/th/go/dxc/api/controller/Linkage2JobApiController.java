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
import th.go.dxc.app.model.JobLinkage2;
import th.go.dxc.app.service.Linkage2Service;
import th.go.dxc.infra.connector.dopalinkage2.model.request.Linkage2TokenRequest;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntity;
import th.go.dxc.share.commons.dto.ErrorDto;
import th.go.dxc.share.security.model.DxcUserDetails;
import th.go.dxc.share.security.service.SecurityService;

@Tags(value = { @Tag(name = "บริการค้นหาข้อมูล Job Linkage2") })
@RestController
@RequestMapping("/api/moi/linkage2/user")
public class Linkage2JobApiController {

	private Linkage2Service service;
	private final SecurityService securityService;
	
	public Linkage2JobApiController(Linkage2Service service, SecurityService securityService) {
		super();
		this.service = service;
		this.securityService = securityService;
	}
	
	@Operation(summary = "ข้อมูลกระบวนงานที่มีสิทธิ",security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = JobLinkage2.class))),
		
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
	@RequestMapping(method = RequestMethod.POST, value = "/job")
	@ResponseBody
	public Mono<JobLinkage2> jobLinkage2(@RequestBody Linkage2TokenRequest request) {
		// ใช้แบบ reactive
		return securityService.getCurrentUser()
				.flatMap(currentUser ->  service.jobLinkage2(request, currentUser.getUserOrganizationId()));
	}
	
}
