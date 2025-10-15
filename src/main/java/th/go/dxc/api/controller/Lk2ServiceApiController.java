package th.go.dxc.api.controller;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
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
import reactor.core.publisher.Mono;
import th.go.dxc.app.model.Lk2Service;
import th.go.dxc.app.model.Lk2ServiceFilter;
import th.go.dxc.app.service.Linkage2Service;
import th.go.dxc.share.commons.dto.ErrorDto;
import th.go.dxc.share.commons.dto.PageDto;
import th.go.dxc.share.commons.dto.PageRequestDto;
import th.go.dxc.share.commons.util.ObjectMapperService;

@Tags(value = { @Tag(name = "บริการค้นหาข้อมูล Lk2Service") })
@RestController
@RequestMapping("/api/qm/dxc/lk2service")
public class Lk2ServiceApiController {

	private Linkage2Service service;
	private final ObjectMapperService mapper;
	
	public Lk2ServiceApiController(Linkage2Service service, ObjectMapperService mapper) {
		super();
		this.service = service;
		this.mapper = mapper;
	}
	
	@Operation(summary = "บริการค้นหาข้อมูล ฐานข้อมูล linkage2",security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = Lk2Service.class))),
		
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
	public Mono<PageDto<Lk2Service>> findAll(@Valid @ParameterObject Lk2ServiceFilter filter, @Valid @ParameterObject PageRequestDto pageableDto) {
		Pageable pageable = mapper.mapPageable(pageableDto);
		return service.findAll(filter, pageable) // Mono<Page<Lk2Service>>
				.map(resultPage -> mapper.mapPageDto(resultPage));
	}
}
