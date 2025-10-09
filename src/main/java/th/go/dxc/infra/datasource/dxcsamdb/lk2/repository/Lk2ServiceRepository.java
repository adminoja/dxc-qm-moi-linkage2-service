package th.go.dxc.infra.datasource.dxcsamdb.lk2.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntityFilter;

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
				+ "and (:#{#filter.departmentJob} is null or a.departmentJob = :#{#filter.departmentJob})"
				+ "and (:#{#filter.ipproxy} is null or a.ipproxy = :#{#filter.ipproxy})"
				+ "and (:#{#filter.jobId} is null or a.jobId = :#{#filter.jobId})"
			, value = "SELECT * FROM " + Lk2ServiceEntity.ENTITY_TABLE_NAME + " a "
				+ "where (:#{#filter.id} is null or a.id = :#{#filter.id})"
				+ "and (:#{#filter.department} is null or a.department = :#{#filter.department})"
				+ "and (:#{#filter.serviceName} is null or a.serviceName = :#{#filter.serviceName})"
				+ "and (:#{#filter.serviceNameUnderDXC} is null or a.serviceNameUnderDXC = :#{#filter.serviceNameUnderDXC})"
				+ "and (:#{#filter.jobName} is null or a.jobName = :#{#filter.jobName})"
				+ "and (:#{#filter.serviceId} is null or a.serviceId = :#{#filter.serviceId})"
				+ "and (:#{#filter.departmentJob} is null or a.departmentJob = :#{#filter.departmentJob})"
				+ "and (:#{#filter.ipproxy} is null or a.ipproxy = :#{#filter.ipproxy})"
				+ "and (:#{#filter.jobId} is null or a.jobId = :#{#filter.jobId})"
			)
	public Page<Lk2ServiceEntity> findByFilterNative(Lk2ServiceEntityFilter filter, Pageable pageable);

	@Query(value = "select s from Lk2ServiceEntity s where s.jobId = ?1")
	public List<Lk2ServiceEntity> findByJobId(String jobId);
}
