package th.go.dxc.infra.datasource.dxcsamdb.lk2.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntity;

public interface Lk2ServiceRepository extends PagingAndSortingRepository<Lk2ServiceEntity, Integer>, QueryByExampleExecutor<Lk2ServiceEntity>, JpaSpecificationExecutor<Lk2ServiceEntity> {
//	@Query(nativeQuery = true, countQuery = "")
//	public Page<Lk2ServiceEntity> findByFilterNative(Lk2ServiceEntityFilter filter, Pageable pageable);

	@Query(value = "select s from Lk2ServiceEntity s where s.jobId = ?1")
	public List<Lk2ServiceEntity> findByJobId(String jobId);
}
