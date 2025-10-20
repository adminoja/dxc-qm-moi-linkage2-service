package th.go.dxc.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
import th.go.dxc.app.model.Lk2TokenService;
import th.go.dxc.app.model.MoeOpsGraduate;
import th.go.dxc.app.model.MoeOpsStudent;
import th.go.dxc.app.model.MoiDopaAlien;
import th.go.dxc.app.model.MoiDopaBirthCertificate;
import th.go.dxc.app.model.MoiDopaDivorceCertificate;
import th.go.dxc.app.model.MoiDopaMarriageCertificate;
import th.go.dxc.app.model.MoiDopaPerson;
import th.go.dxc.app.model.MoiDopaPersonChangeLastnamePrimary;
import th.go.dxc.app.model.MoiDopaPersonChangeNamePrimary;
import th.go.dxc.app.model.MoiDopaPersonFacePhoto;
import th.go.dxc.app.model.MolDsdWorkforceDevelopment;
import th.go.dxc.app.model.SearchPersons;
import th.go.dxc.app.service.Linkage2Service;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse.ResponseItem;
import th.go.dxc.share.commons.dto.ErrorDto;
import th.go.dxc.share.security.service.SecurityService;

@Tags(value = { @Tag(name = "บริการค้นหาข้อมูล Linkage2") })
@RestController
@RequestMapping("/api/moi/linkage2/persons")
public class Linkage2ApiController {
	
	private Linkage2Service service;
	private final SecurityService securityService;
	
	public Linkage2ApiController(Linkage2Service service, SecurityService securityService) {
		super();
		this.service = service;
		this.securityService = securityService;
	}
	
	@Operation(summary = "บริการค้นหาข้อมูล ทะเบียนราษฎร", security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = MoiDopaPerson.class))),
		
//		@ApiResponse(responseCode = "400",description = "เรียกใช้งานไม่ถูกต้อง"
//		,content = @Content(mediaType = "application/json"
//		, schema = @Schema(implementation = ErrorDto.class))),
//
//		@ApiResponse(responseCode = "401",description = "การยืนยันตัวตนไม่ถูกต้อง Token หรือ รหัสยืนยันตัวตนมีปัญหา"
//		,headers = {@Header(name = "www-authenticate",description = "รายละเอียดข้อผิดพลาด (ถ้ามี)")}
//		,content = @Content(schema = @Schema(hidden=true))),
//
//		@ApiResponse(responseCode = "403",description = "ไม่มีสิทธิในการใช้บริการ"
//		,content = @Content(mediaType = "application/json"
//		, schema = @Schema(implementation = ErrorDto.class))),
//		
//		@ApiResponse(responseCode = "500",description = "ระบบทำงานผิดพลาด กรุณาติดต่อผู้ดูแลระบบ"
//		,content = @Content(mediaType = "application/json"
//		, schema = @Schema(implementation = ErrorDto.class)))
	})
	@GetMapping("/{thaiNin}/moi-dopa-persons")
//	public Mono<Page<ResponseItem<Object>>> findMoiDopaPersons (
	public Mono<Page<MoiDopaPerson>> findMoiDopaPersons (
			@Parameter(description = "เลขประจำตัวประชาชนไทยผู้ค้น") @RequestHeader(value = "X-User-Nin", required = true) String userNin,
			@Parameter(description = "เลขประจำตัวประชาชนไทยข้อมูล", required = false) @PathVariable(value = "thaiNin", required = false) String thaiNin,
			@Parameter(description = "รหัสฐานข้อมูล") @RequestParam(value = "serviceId", required = true) String serviceId) {
//		return service.findMoiDopaPersons(userNin, thaiNin, serviceId);
		return securityService.getCurrentUser()
				.flatMap(currentUser -> {
					String departmentCode = currentUser.getUserOrganizationId(); // ✅ หน่วยงานของ user
					return service.findByServiceIdAndDepartmentCode(serviceId, departmentCode)
							.flatMap(lk2Service -> {
								return service.findMoiDopaPersons(userNin, thaiNin, lk2Service.getJobId(), departmentCode);  // ✅ ดึง jobId ที่ตรงกับหน่วยงาน
							});
				});
	}
	
//	@Operation(summary = "บริการค้นหาข้อมูล ทะเบียนราษฎร", security = @SecurityRequirement(name="bearerAuth"))
//	@PostMapping("/{thaiNin}/moi-dopa-persons")
//	@ResponseBody
//	public Mono<Page<ResponseItem<Object>>> findMoiDopaPersons (@RequestBody SearchPersons request) {
////		return service.findMoiDopaPersons(userNin, thaiNin, serviceId);
//		return securityService.getCurrentUser()
//				.flatMap(currentUser -> {
//					String departmentCode = currentUser.getUserOrganizationId(); // ✅ หน่วยงานของ user
//					return service.findByServiceIdAndDepartmentCode(request.getServiceId(), departmentCode)
//							.flatMap(lk2Service -> {
//								return service.findMoiDopaPersons(request.getUserNin(), request.getThaiNin(), lk2Service.getJobId());  // ✅ ดึง jobId ที่ตรงกับหน่วยงาน
//							});
//				});
//	}
	
	@Operation(summary = "บริการค้นหาข้อมูล การจดทะเบียนเปลี่ยนชื่อตัว", security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = MoiDopaPersonChangeNamePrimary.class))),
	})
	@GetMapping("/{thaiNin}/moi-dopa-person-changename-primary")
	public Mono<Page<MoiDopaPersonChangeNamePrimary>> findMoiDopaPersonChangeNamePrimary (
			@Parameter(description = "เลขประจำตัวประชาชนไทยผู้ค้น") @RequestHeader(value = "X-User-Nin", required = true) String userNin,
			@Parameter(description = "เลขประจำตัวประชาชนไทยข้อมูล", required = false) @PathVariable(value = "thaiNin", required = false) String thaiNin,
			@Parameter(description = "รหัสฐานข้อมูล") @RequestParam(value = "serviceId", required = true) String serviceId) {
		return securityService.getCurrentUser()
				.flatMap(currentUser -> {
					String departmentCode = currentUser.getUserOrganizationId(); // ✅ หน่วยงานของ user
					return service.findByServiceIdAndDepartmentCode(serviceId, departmentCode)
							.flatMap(lk2Service -> {
								return service.findMoiDopaPersonChangeNamePrimary(userNin, thaiNin, lk2Service.getJobId(), departmentCode);  // ✅ ดึง jobId ที่ตรงกับหน่วยงาน
							});
				});
	}
	
	@Operation(summary = "บริการค้นหาข้อมูล การจดทะเบียนเปลี่ยนชื่อสกุล", security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = MoiDopaPersonChangeLastnamePrimary.class))),
	})
	@GetMapping("/{thaiNin}/moi-dopa-person-changelastname-primary")
	public Mono<Page<MoiDopaPersonChangeLastnamePrimary>> findDopaPersonChangeLastnamePrimary (
			@Parameter(description = "เลขประจำตัวประชาชนไทยผู้ค้น") @RequestHeader(value = "X-User-Nin", required = true) String userNin,
			@Parameter(description = "เลขประจำตัวประชาชนไทยข้อมูล", required = false) @PathVariable(value = "thaiNin", required = false) String thaiNin,
			@Parameter(description = "รหัสฐานข้อมูล") @RequestParam(value = "serviceId", required = true) String serviceId) {
		return securityService.getCurrentUser()
				.flatMap(currentUser -> {
					String departmentCode = currentUser.getUserOrganizationId(); // ✅ หน่วยงานของ user
					return service.findByServiceIdAndDepartmentCode(serviceId, departmentCode)
							.flatMap(lk2Service -> {
								return service.findDopaPersonChangeLastnamePrimary(userNin, thaiNin, lk2Service.getJobId(), departmentCode);  // ✅ ดึง jobId ที่ตรงกับหน่วยงาน
							});
				});
	}
	
	@Operation(summary = "บริการค้นหาข้อมูล ทะเบียนบุคคลต่างด้าว", security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = MoiDopaAlien.class))),
	})
	@GetMapping("/{thaiNin}/moi-dopa-aliens")
	public Mono<Page<MoiDopaAlien>> findMoiDopaAlien (
			@Parameter(description = "เลขประจำตัวประชาชนไทยผู้ค้น") @RequestHeader(value = "X-User-Nin", required = true) String userNin,
			@Parameter(description = "เลขประจำตัวประชาชนไทยข้อมูล", required = false) @PathVariable(value = "thaiNin", required = false) String thaiNin,
			@Parameter(description = "รหัสฐานข้อมูล") @RequestParam(value = "serviceId", required = true) String serviceId) {
		return securityService.getCurrentUser()
				.flatMap(currentUser -> {
					String departmentCode = currentUser.getUserOrganizationId(); // ✅ หน่วยงานของ user
					return service.findByServiceIdAndDepartmentCode(serviceId, departmentCode)
							.flatMap(lk2Service -> {
								return service.findMoiDopaAlien(userNin, thaiNin, lk2Service.getJobId(), departmentCode);  // ✅ ดึง jobId ที่ตรงกับหน่วยงาน
							});
				});
	}
	
	@Operation(summary = "บริการค้นหาข้อมูล ทะเบียนการหย่า", security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = MoiDopaDivorceCertificate.class))),
	})
	@GetMapping("/{thaiNin}/moi-dopa-divorce-certificates")
	public Mono<Page<MoiDopaDivorceCertificate>> findMoiDopaDivorceCertificate (
			@Parameter(description = "เลขประจำตัวประชาชนไทยผู้ค้น") @RequestHeader(value = "X-User-Nin", required = true) String userNin,
			@Parameter(description = "เลขประจำตัวประชาชนไทยข้อมูล", required = false) @PathVariable(value = "thaiNin", required = false) String thaiNin,
			@Parameter(description = "รหัสฐานข้อมูล") @RequestParam(value = "serviceId", required = true) String serviceId) {
		return securityService.getCurrentUser()
				.flatMap(currentUser -> {
					String departmentCode = currentUser.getUserOrganizationId(); // ✅ หน่วยงานของ user
					return service.findByServiceIdAndDepartmentCode(serviceId, departmentCode)
							.flatMap(lk2Service -> {
								return service.findMoiDopaDivorceCertificate(userNin, thaiNin, lk2Service.getJobId(), departmentCode);  // ✅ ดึง jobId ที่ตรงกับหน่วยงาน
							});
				});
	}
	
	@Operation(summary = "บริการค้นหาข้อมูล ใบสูติบัตร", security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = MoiDopaBirthCertificate.class))),
	})
	@GetMapping("/{thaiNin}/moi-dopa-birth-certificates")
	public Mono<Page<MoiDopaBirthCertificate>> findMoiDopaBirthCertificate (
			@Parameter(description = "เลขประจำตัวประชาชนไทยผู้ค้น") @RequestHeader(value = "X-User-Nin", required = true) String userNin,
			@Parameter(description = "เลขประจำตัวประชาชนไทยข้อมูล", required = false) @PathVariable(value = "thaiNin", required = false) String thaiNin,
			@Parameter(description = "รหัสฐานข้อมูล") @RequestParam(value = "serviceId", required = true) String serviceId) {
		return securityService.getCurrentUser()
				.flatMap(currentUser -> {
					String departmentCode = currentUser.getUserOrganizationId(); // ✅ หน่วยงานของ user
					return service.findByServiceIdAndDepartmentCode(serviceId, departmentCode)
							.flatMap(lk2Service -> {
								return service.findMoiDopaBirthCertificate(userNin, thaiNin, lk2Service.getJobId(), departmentCode);  // ✅ ดึง jobId ที่ตรงกับหน่วยงาน
							});
				});
	}
	
	@Operation(summary = "บริการค้นหาข้อมูล ภาพใบหน้า", security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = MoiDopaPersonFacePhoto.class))),
	})
	@GetMapping("/{thaiNin}/moi-dopa-person-face-photos")
	public Mono<Page<MoiDopaPersonFacePhoto>> findMoiDopaPersonFacePhoto (
			@Parameter(description = "เลขประจำตัวประชาชนไทยผู้ค้น") @RequestHeader(value = "X-User-Nin", required = true) String userNin,
			@Parameter(description = "เลขประจำตัวประชาชนไทยข้อมูล", required = false) @PathVariable(value = "thaiNin", required = false) String thaiNin,
			@Parameter(description = "รหัสฐานข้อมูล") @RequestParam(value = "serviceId", required = true) String serviceId) {
		return securityService.getCurrentUser()
				.flatMap(currentUser -> {
					String departmentCode = currentUser.getUserOrganizationId(); // ✅ หน่วยงานของ user
					return service.findByServiceIdAndDepartmentCode(serviceId, departmentCode)
							.flatMap(lk2Service -> {
								return service.findMoiDopaPersonFacePhoto(userNin, thaiNin, lk2Service.getJobId(), departmentCode);  // ✅ ดึง jobId ที่ตรงกับหน่วยงาน
							});
				});
	}
	
	@Operation(summary = "บริการค้นหาข้อมูล ทะเบียนสมรส", security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = MoiDopaMarriageCertificate.class))),
	})
	@GetMapping("/{thaiNin}/moi-dopa-marriage-certificates")
	public Mono<Page<MoiDopaMarriageCertificate>> findMoiDopaMarriageCertificate (
			@Parameter(description = "เลขประจำตัวประชาชนไทยผู้ค้น") @RequestHeader(value = "X-User-Nin", required = true) String userNin,
			@Parameter(description = "เลขประจำตัวประชาชนไทยข้อมูล", required = false) @PathVariable(value = "thaiNin", required = false) String thaiNin,
			@Parameter(description = "รหัสฐานข้อมูล") @RequestParam(value = "serviceId", required = true) String serviceId) {
		return securityService.getCurrentUser()
				.flatMap(currentUser -> {
					String departmentCode = currentUser.getUserOrganizationId(); // ✅ หน่วยงานของ user
					return service.findByServiceIdAndDepartmentCode(serviceId, departmentCode)
							.flatMap(lk2Service -> {
								return service.findMoiDopaMarriageCertificate(userNin, thaiNin, lk2Service.getJobId(), departmentCode);  // ✅ ดึง jobId ที่ตรงกับหน่วยงาน
							});
				});
	}
	
	@Operation(summary = "บริการค้นหาข้อมูล นักเรียน", security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = MoeOpsStudent.class))),
	})
	@GetMapping("/{thaiNin}/moe-ops-student")
	public Mono<Page<MoeOpsStudent>> findMoeStudent (
			@Parameter(description = "เลขประจำตัวประชาชนไทยผู้ค้น") @RequestHeader(value = "X-User-Nin", required = true) String userNin,
			@Parameter(description = "เลขประจำตัวประชาชนไทยข้อมูล", required = false) @PathVariable(value = "thaiNin", required = false) String thaiNin,
			@Parameter(description = "รหัสฐานข้อมูล") @RequestParam(value = "serviceId", required = true) String serviceId) {
		return securityService.getCurrentUser()
				.flatMap(currentUser -> {
					String departmentCode = currentUser.getUserOrganizationId(); // ✅ หน่วยงานของ user
					return service.findByServiceIdAndDepartmentCode(serviceId, departmentCode)
							.flatMap(lk2Service -> {
								return service.findMoeOpsStudent(userNin, thaiNin, lk2Service.getJobId(), departmentCode);  // ✅ ดึง jobId ที่ตรงกับหน่วยงาน
							});
				});
	}
	
	@Operation(summary = "บริการค้นหาข้อมูล ผู้สำเร็จการศึกษา", security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = MoeOpsGraduate.class))),
	})
	@GetMapping("/{thaiNin}/moe-ops-graduate")
	public Mono<Page<MoeOpsGraduate>> findMoeOpsGraduate (
			@Parameter(description = "เลขประจำตัวประชาชนไทยผู้ค้น") @RequestHeader(value = "X-User-Nin", required = true) String userNin,
			@Parameter(description = "เลขประจำตัวประชาชนไทยข้อมูล", required = false) @PathVariable(value = "thaiNin", required = false) String thaiNin,
			@Parameter(description = "รหัสฐานข้อมูล") @RequestParam(value = "serviceId", required = true) String serviceId) {
		return securityService.getCurrentUser()
				.flatMap(currentUser -> {
					String departmentCode = currentUser.getUserOrganizationId(); // ✅ หน่วยงานของ user
					return service.findByServiceIdAndDepartmentCode(serviceId, departmentCode)
							.flatMap(lk2Service -> {
								return service.findMoeOpsGraduate(userNin, thaiNin, lk2Service.getJobId(), departmentCode);  // ✅ ดึง jobId ที่ตรงกับหน่วยงาน
							});
				});
	}
	
	@Operation(summary = "บริการค้นหาข้อมูล การพัฒนาฝีมือแรงงาน", security = @SecurityRequirement(name="bearerAuth"))
	@ApiResponses({
		@ApiResponse(responseCode = "200",description = "ระบบทำงานปกติ"
				,content = @Content(mediaType = "application/json"
				, schema = @Schema(implementation = MolDsdWorkforceDevelopment.class))),
	})
	@GetMapping("/{thaiNin}/mol-dsd-workforce-developments")
	public Mono<Page<MolDsdWorkforceDevelopment>> findMolDsdWorkforceDevelopment (
			@Parameter(description = "เลขประจำตัวประชาชนไทยผู้ค้น") @RequestHeader(value = "X-User-Nin", required = true) String userNin,
			@Parameter(description = "เลขประจำตัวประชาชนไทยข้อมูล", required = false) @PathVariable(value = "thaiNin", required = false) String thaiNin,
			@Parameter(description = "รหัสฐานข้อมูล") @RequestParam(value = "serviceId", required = true) String serviceId) {
		return securityService.getCurrentUser()
				.flatMap(currentUser -> {
					String departmentCode = currentUser.getUserOrganizationId(); // ✅ หน่วยงานของ user
					return service.findByServiceIdAndDepartmentCode(serviceId, departmentCode)
							.flatMap(lk2Service -> {
								return service.findMolDsdWorkforceDevelopment(userNin, thaiNin, lk2Service.getJobId(), departmentCode);  // ✅ ดึง jobId ที่ตรงกับหน่วยงาน
							});
				});
	}
}
