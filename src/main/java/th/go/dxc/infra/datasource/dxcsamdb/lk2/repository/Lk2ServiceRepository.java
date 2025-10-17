package th.go.dxc.infra.datasource.dxcsamdb.lk2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import reactor.core.publisher.Mono;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntityFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ThaidLogEntity;

public interface Lk2ServiceRepository extends PagingAndSortingRepository<Lk2ServiceEntity, Integer>,
		QueryByExampleExecutor<Lk2ServiceEntity>, JpaSpecificationExecutor<Lk2ServiceEntity> {
	@Query(nativeQuery = true, 
			countQuery = "SELECT COUNT(*) FROM " + Lk2ServiceEntity.ENTITY_TABLE_NAME + " a "
				+ "where (:#{#filter.id} is null or a.id = :#{#filter.id})"
				+ "and (:#{#filter.department} is null or a.department = :#{#filter.department})"
				+ "and (:#{#filter.serviceName} is null or a.serviceName = :#{#filter.serviceName})"
				+ "and (:#{#filter.serviceNameUnderDXC} is null or a.serviceNameUnderDXC = :#{#filter.serviceNameUnderDXC})"
				+ "and (:#{#filter.jobName} is null or a.jobName = :#{#filter.jobName})"
				+ "and (:#{#filter.serviceId} is null or a.serviceId = :#{#filter.serviceId})"
				+ "and (:#{#filter.departmentCode} is null or a.departmentCode = :#{#filter.departmentCode})"
				+ "and (:#{#filter.ipProxy} is null or a.ipproxy = :#{#filter.ipProxy})"
				+ "and (:#{#filter.jobId} is null or a.jobId = :#{#filter.jobId})"
				+ "and (:#{#filter.officeId} is null or a.officeId = :#{#filter.officeId})"
			, value = "SELECT * FROM " + Lk2ServiceEntity.ENTITY_TABLE_NAME + " a "
				+ "where (:#{#filter.id} is null or a.id = :#{#filter.id})"
				+ "and (:#{#filter.department} is null or a.department = :#{#filter.department})"
				+ "and (:#{#filter.serviceName} is null or a.serviceName = :#{#filter.serviceName})"
				+ "and (:#{#filter.serviceNameUnderDXC} is null or a.serviceNameUnderDXC = :#{#filter.serviceNameUnderDXC})"
				+ "and (:#{#filter.jobName} is null or a.jobName = :#{#filter.jobName})"
				+ "and (:#{#filter.serviceId} is null or a.serviceId = :#{#filter.serviceId})"
				+ "and (:#{#filter.departmentCode} is null or a.departmentCode = :#{#filter.departmentCode})"
				+ "and (:#{#filter.ipProxy} is null or a.ipproxy = :#{#filter.ipProxy})"
				+ "and (:#{#filter.jobId} is null or a.jobId = :#{#filter.jobId})"
				+ "and (:#{#filter.officeId} is null or a.officeId = :#{#filter.officeId})"
			)
	public Page<Lk2ServiceEntity> findByFilterNative(Lk2ServiceEntityFilter filter, Pageable pageable);
	
	@Query(value = "SELECT s FROM Lk2ServiceEntity s WHERE s.jobId = ?1")
	public Optional<Lk2ServiceEntity> findByJobId(String jobId);
	
	@Query(value = "SELECT s FROM Lk2ServiceEntity s WHERE s.serviceId = ?1 and s.departmentCode =?2")
	public Optional<Lk2ServiceEntity> findByServiceIdAndDepartmentCode(String serviceId, String departmentCode);
	
	@Query(value = "SELECT s FROM Lk2ServiceEntity s WHERE s.departmentCode = ?1")
	public List<Lk2ServiceEntity> findByDepartmentCode(String departmentCode);
}
