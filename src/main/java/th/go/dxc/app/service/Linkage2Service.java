package th.go.dxc.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Mono;
import th.go.dxc.app.model.AmloAssetFreezePersons;
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
import th.go.dxc.app.model.MoiDopaPersonFindByName;
import th.go.dxc.app.model.MolDsdWorkforceDevelopment;
import th.go.dxc.infra.connector.dopalinkage2.model.request.Linkage2TokenRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse.ResponseItem;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntity;

public interface Linkage2Service {

	Mono<List<Lk2ServiceEntity>> findByDepartmentCodeLk2Service(String departmentCode);

	Mono<JobLinkage2> jobLinkage2(Linkage2TokenRequest request, String departmentCode);

	Mono<Page<Lk2Service>> findAll(Lk2ServiceFilter filter, Pageable pageable);

	Mono<Lk2ServiceEntity> findByServiceIdAndDepartmentCode(String serviceId, String departmentCode);
	
//	Mono<Page<ResponseItem<Object>>> findMoiDopaPersons(String userNin, String thaiNin, String jobId);
	Mono<Page<MoiDopaPerson>> findMoiDopaPersons(String userNin, String thaiNin, String jobId, String departmentCode);

	Mono<Page<MoeOpsStudent>> findMoeOpsStudent(String userNin, String thaiNin, String jobId, String departmentCode);

	Mono<Page<MoeOpsGraduate>> findMoeOpsGraduate(String userNin, String thaiNin, String jobId, String departmentCode);

	Mono<Page<MolDsdWorkforceDevelopment>> findMolDsdWorkforceDevelopment(String userNin, String thaiNin, String jobId, String departmentCode);

	Mono<Page<MoiDopaPersonChangeNamePrimary>> findMoiDopaPersonChangeNamePrimary(String userNin, String thaiNin, String jobId, String departmentCode);

	Mono<Page<MoiDopaPersonChangeLastnamePrimary>> findDopaPersonChangeLastnamePrimary(String userNin, String thaiNin, String jobId, String departmentCode);

	Mono<Page<MoiDopaAlien>> findMoiDopaAlien(String userNin, String thaiNin, String jobId, String departmentCode);

	Mono<Page<MoiDopaDivorceCertificate>> findMoiDopaDivorceCertificate(String userNin, String thaiNin, String jobId, String departmentCode);

	Mono<Page<MoiDopaBirthCertificate>> findMoiDopaBirthCertificate(String userNin, String thaiNin, String jobId, String departmentCode);

	Mono<Page<MoiDopaPersonFacePhoto>> findMoiDopaPersonFacePhoto(String userNin, String thaiNin, String jobId, String departmentCode);

	Mono<Page<MoiDopaMarriageCertificate>> findMoiDopaMarriageCertificate(String userNin, String thaiNin, String jobId, String departmentCode);

	Mono<Page<MoiDopaThaiIdCard>> findMoiDopaThaiIdCard(String userNin, String thaiNin, String jobId, String departmentCode);

	Mono<Page<MoiDopaAddress>> findMoiDopaAddress(String userNin, String thaiNin, String jobId, String departmentCode);

	Mono<Page<MoiDopaPersonFindByName>> findMoiDopaPersonByName(String userNin, String firstName, String lastName, String recordNumber, String jobId, String departmentCode);

	Mono<Page<AmloAssetFreezePersons>> findAmloAssetFreezePersons(String userNin, String thaiNin, String jobId, String departmentCode);

}
