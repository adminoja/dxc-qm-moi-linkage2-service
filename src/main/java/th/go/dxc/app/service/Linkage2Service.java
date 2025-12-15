package th.go.dxc.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Mono;
import th.go.dxc.app.model.AmloAssetFreezePerson;
import th.go.dxc.app.model.MsdhsDepCripple;
import th.go.dxc.app.model.JobLinkage2;
import th.go.dxc.app.model.Lk2Service;
import th.go.dxc.app.model.Lk2ServiceFilter;
import th.go.dxc.app.model.MoeOpsGraduate;
import th.go.dxc.app.model.MoeOpsStudent;
import th.go.dxc.app.model.MoiDopaAddress;
import th.go.dxc.app.model.MoiDopaAlien;
import th.go.dxc.app.model.MoiDopaBirthCertificate;
import th.go.dxc.app.model.MoiDopaDivorceCertificate;
import th.go.dxc.app.model.MoiDopaMarriageCertificate;
import th.go.dxc.app.model.MoiDopaPerson;
import th.go.dxc.app.model.MoiDopaPersonChangeLastnamePrimary;
import th.go.dxc.app.model.MoiDopaPersonChangeNamePrimary;
import th.go.dxc.app.model.MoiDopaPersonFacePhoto;
import th.go.dxc.app.model.MoiDopaPersonFirstnameLastname;
import th.go.dxc.app.model.MoiDopaPor4License;
import th.go.dxc.app.model.MolDsdWorkforceDevelopment;
import th.go.dxc.app.model.MophNhsoHealthInsuranceRight;
import th.go.dxc.infra.connector.dopalinkage2.model.request.Linkage2TokenRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse.ResponseItem;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntity;

public interface Linkage2Service {

	Mono<List<Lk2ServiceEntity>> findByDepartmentCodeLk2Service(String departmentCode);

	Mono<JobLinkage2> jobLinkage2(Linkage2TokenRequest request, String departmentCode);

	Mono<Page<Lk2Service>> findAll(Lk2ServiceFilter filter, Pageable pageable);

	Mono<Lk2ServiceEntity> findByServiceIdAndDepartmentCode(String serviceId, String departmentCode);
	
	// ฐานข้อมูลทะเบียนราษฎร
//	Mono<Page<ResponseItem<Object>>> findMoiDopaPersons(String userNin, String thaiNin, String jobId);
	Mono<Page<MoiDopaPerson>> findMoiDopaPerson(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลนักเรียน
	Mono<Page<MoeOpsStudent>> findMoeOpsStudent(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลผู้สำเร็จการศึกษา
	Mono<Page<MoeOpsGraduate>> findMoeOpsGraduate(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลการพัฒนาฝีมือแรงงาน
	Mono<Page<MolDsdWorkforceDevelopment>> findMolDsdWorkforceDevelopment(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลการจดทะเบียนเปลี่ยนชื่อตัว
	Mono<Page<MoiDopaPersonChangeNamePrimary>> findMoiDopaPersonChangeNamePrimary(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลการจดทะเบียนเปลี่ยนชื่อสกุล
	Mono<Page<MoiDopaPersonChangeLastnamePrimary>> findDopaPersonChangeLastnamePrimary(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลทะเบียนบุคคลต่างด้าว
	Mono<Page<MoiDopaAlien>> findMoiDopaAlien(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลทะเบียนการหย่า
	Mono<Page<MoiDopaDivorceCertificate>> findMoiDopaDivorceCertificate(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลใบสูติบัตร
	Mono<Page<MoiDopaBirthCertificate>> findMoiDopaBirthCertificate(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลภาพใบหน้า
	Mono<Page<MoiDopaPersonFacePhoto>> findMoiDopaPersonFacePhoto(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลทะเบียนสมรส
	Mono<Page<MoiDopaMarriageCertificate>> findMoiDopaMarriageCertificate(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลบัตรประจำตัวประชาชน
	Mono<Page<MoiDopaThaiIdCard>> findMoiDopaThaiIdCard(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลทะเบียนบ้าน (บุคคลทุกประเภท)
	Mono<Page<MoiDopaAddress>> findMoiDopaAddress(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลทะเบียนราษฎร (ค้นหาด้วยชื่อตัว-ชื่อสกุล)
	Mono<Page<MoiDopaPersonFirstnameLastname>> findMoiDopaPersonFirstnameLastname(String userNin, String firstName, String lastName, String recordNumber, String jobId, String departmentCode, String sessionStateKc);

	// "ฐานข้อมูลรายชื่อบุคคลที่ถูกยึดหรืออายัดทรัพย์สิน (HR-02)
	Mono<Page<AmloAssetFreezePerson>> findAmloAssetFreezePerson(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลคนพิการ
	Mono<Page<MsdhsDepCripple>> findMsdhsDepCripple(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลใบอนุญาตป.4
	Mono<Page<MoiDopaPor4License>> findMoiDopaPor4License(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

	// ฐานข้อมูลสิทธิประกันสุขภาพและการลงทะเบียนกับหน่วยบริการ
	Mono<Page<MophNhsoHealthInsuranceRight>> findMophNhsoHealthInsuranceRight(String userNin, String thaiNin, String jobId, String departmentCode, String sessionStateKc);

}
