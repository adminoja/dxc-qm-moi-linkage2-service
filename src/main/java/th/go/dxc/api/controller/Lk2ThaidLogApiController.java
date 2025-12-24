package th.go.dxc.api.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
import jakarta.validation.Valid;
import th.go.dxc.app.model.Lk2ThaidLog;
import th.go.dxc.app.model.Lk2ThaidLogFilter;
import th.go.dxc.app.service.Lk2ThaidLogService;
import th.go.dxc.share.commons.dto.ErrorDto;
import th.go.dxc.share.commons.dto.PageDto;
import th.go.dxc.share.commons.dto.PageRequestDto;
import th.go.dxc.share.commons.util.ObjectMapperService;

@Tags(value = { @Tag(name = "บริการ Lk2ThaidLog") })
@RestController
@RequestMapping("/api/qm/dxc/lk2thaidLog")
public class Lk2ThaidLogApiController {
	
	private Lk2ThaidLogService service;
	private final ObjectMapperService mapper;
	
	public Lk2ThaidLogApiController(Lk2ThaidLogService service, ObjectMapperService mapper) {
		super();
		this.service = service;
		this.mapper = mapper;
	}
	
	@Operation(summary = "บริการค้นหาข้อมูล Log ThaID Token",security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = Lk2ThaidLog.class))),
		
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
	@GetMapping("/findAll")
	public PageDto<Lk2ThaidLog> findAll(@Valid @ParameterObject Lk2ThaidLogFilter filter, @Valid @ParameterObject PageRequestDto pageableDto) {
		Pageable pageable = mapper.mapPageable(pageableDto);
		Page<Lk2ThaidLog> resultPage = service.findAll(filter, pageable);
		return mapper.mapPageDto(resultPage);
	}
}
