package th.go.dxc.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import reactor.core.publisher.Mono;
import th.go.dxc.app.model.LoginLinkage2Token;
import th.go.dxc.app.model.MoiDopaPerson;
import th.go.dxc.app.model.JobLinkage2;
import th.go.dxc.app.model.Lk2Service;
import th.go.dxc.app.model.Lk2ServiceFilter;
import th.go.dxc.app.model.LoginLinkage2;
import th.go.dxc.infra.connector.dopalinkage2.model.request.ConfirmLoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.Linkage2TokenRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.request.LoginLinkage2Request;
import th.go.dxc.infra.connector.dopalinkage2.model.request.UsernameRequest;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse;
import th.go.dxc.infra.connector.dopalinkage2.model.response.GenericResponse.ResponseItem;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntity;

public interface Linkage2Service {

	String departmentCodeKeycloakFromToken();

	Mono<List<Lk2ServiceEntity>> findByDepartmentCodeLk2Service(String departmentCode);

	Mono<JobLinkage2> jobLinkage2(Linkage2TokenRequest request, String departmentCode);

	Mono<Page<Lk2Service>> findAll(Lk2ServiceFilter filter, Pageable pageable);

	Mono<Page<GenericResponse.ResponseItem<Object>>> findMoiDopaPersons(String userNin, String thaiNin, String jobId);

}
