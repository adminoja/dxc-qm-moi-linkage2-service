package th.go.dxc.api.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import th.go.dxc.app.model.MoiDopaPerson;
import th.go.dxc.app.service.Linkage2Service;
import th.go.dxc.infra.connector.dopalinkage2.model.request.Linkage2TokenRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse.ResponseItem;
import th.go.dxc.share.commons.dto.ErrorDto;
import th.go.dxc.share.commons.dto.PageDto;
import th.go.dxc.share.commons.dto.PageRequestDto;
import th.go.dxc.share.commons.util.ObjectMapperService;

@Tags(value = { @Tag(name = "บริการค้นหาข้อมูล Linkage2") })
@RestController
@RequestMapping("/api/v2/moi/linkage2/persons")
public class Linkage2ApiController {
	
	private Linkage2Service service;
	private final ObjectMapperService mapper;
	
	public Linkage2ApiController(Linkage2Service service, ObjectMapperService mapper) {
		super();
		this.service = service;
		this.mapper = mapper;
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
		return service.jobLinkage2(request);
	}
	
//	@Operation(summary = "บริการค้นหาข้อมูล ทะเบียนราษฎร", security = @SecurityRequirement(name="bearerAuth"))
////	@Operation(summary = "ข้อมูลกระบวนงานที่มีสิทธิ",security = @SecurityRequirement(name="bearerAuth"))
//	@RequestMapping(method = RequestMethod.GET, path = { "/{thaiNin}/moi-dopa-persons" })
//	public PageDto<GenericResponse.ResponseItem<Object>> findMoiDopaPersons (
//			@Parameter(description = "เลขประจำตัวประชาชนไทยผู้ค้น") @RequestHeader(value = "X-User-Nin", required = true) String userNin,
//			@Parameter(description = "เลขประจำตัวประชาชนไทยข้อมูล") @RequestParam(value = "thaiNin", required = false) String thaiNin,
//			@Parameter(description = "รหัส Job") @RequestParam(value = "jobId", required = true) String jobId,
//			@Valid @ParameterObject PageRequestDto pageableDto) {
//		Pageable pageable = mapper.mapPageable(pageableDto);
//		Page<GenericResponse.ResponseItem<Object>> resultPage = service.findMoiDopaPersons(userNin, thaiNin, jobId, pageable);
//		return mapper.mapPageDto(resultPage);
//	}
	
	@Operation(summary = "บริการค้นหาข้อมูล ทะเบียนราษฎร", security = @SecurityRequirement(name="bearerAuth"))
//	@Operation(summary = "ข้อมูลกระบวนงานที่มีสิทธิ",security = @SecurityRequirement(name="bearerAuth"))
	@RequestMapping(method = RequestMethod.GET, path = { "/{thaiNin}/moi-dopa-persons" })
	public Mono<Page<ResponseItem<Object>>> findMoiDopaPersons (
			@Parameter(description = "เลขประจำตัวประชาชนไทยผู้ค้น") @RequestHeader(value = "X-User-Nin", required = true) String userNin,
			@Parameter(description = "เลขประจำตัวประชาชนไทยข้อมูล") @RequestParam(value = "thaiNin", required = false) String thaiNin,
			@Parameter(description = "รหัส Job") @RequestParam(value = "jobId", required = true) String jobId) {

		return service.findMoiDopaPersons(userNin, thaiNin, jobId);
	}
}
