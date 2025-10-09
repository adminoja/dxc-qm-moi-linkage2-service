package th.go.dxc.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import reactor.core.publisher.Mono;
import th.go.dxc.app.service.Linkage2Service;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse.ResponseItem;

@Tags(value = { @Tag(name = "บริการค้นหาข้อมูล Linkage2") })
@RestController
@RequestMapping("/api/moi/linkage2/persons")
public class Linkage2ApiController {
	
	private Linkage2Service service;
	
	public Linkage2ApiController(Linkage2Service service) {
		super();
		this.service = service;
	}
	
	@Operation(summary = "บริการค้นหาข้อมูล ทะเบียนราษฎร", security = @SecurityRequirement(name="bearerAuth"))
	@GetMapping("/{thaiNin}/moi-dopa-persons")
	public Mono<Page<ResponseItem<Object>>> findMoiDopaPersons (
			@Parameter(description = "เลขประจำตัวประชาชนไทยผู้ค้น") @RequestHeader(value = "X-User-Nin", required = true) String userNin,
			@Parameter(description = "เลขประจำตัวประชาชนไทยข้อมูล") @RequestParam(value = "thaiNin", required = false) String thaiNin,
			@Parameter(description = "รหัส Job") @RequestParam(value = "jobId", required = true) String jobId) {
		return service.findMoiDopaPersons(userNin, thaiNin, jobId);
	}
}
