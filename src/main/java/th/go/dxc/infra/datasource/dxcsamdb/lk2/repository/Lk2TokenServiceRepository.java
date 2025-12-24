package th.go.dxc.infra.datasource.dxcsamdb.lk2.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2TokenServiceEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2TokenServiceEntityFilter;

public interface Lk2TokenServiceRepository extends JpaRepository<Lk2TokenServiceEntity, Integer>,
		 JpaSpecificationExecutor<Lk2TokenServiceEntity> {
	
	@Query(nativeQuery = true, 
			countQuery = "SELECT COUNT(*) FROM " + Lk2TokenServiceEntity.ENTITY_TABLE_NAME + " a "
				+ "where (:#{#filter.id} is null or a.id = :#{#filter.id})"
				+ "and (:#{#filter.username} is null or a.username = :#{#filter.username})"
				+ "and (:#{#filter.token} is null or a.token = :#{#filter.token})"
				+ "and (:#{#filter.channel} is null or a.channel = :#{#filter.channel})"
				+ "and (:#{#filter.sessionState} is null or a.sessionState = :#{#filter.sessionState})"
			, value = "SELECT * FROM " + Lk2TokenServiceEntity.ENTITY_TABLE_NAME + " a "
					+ "where (:#{#filter.id} is null or a.id = :#{#filter.id})"
					+ "and (:#{#filter.username} is null or a.username = :#{#filter.username})"
					+ "and (:#{#filter.token} is null or a.token = :#{#filter.token})"
					+ "and (:#{#filter.channel} is null or a.channel = :#{#filter.channel})"
					+ "and (:#{#filter.sessionState} is null or a.sessionState = :#{#filter.sessionState})"
			)
	public Page<Lk2TokenServiceEntity> findByFilterNative(Lk2TokenServiceEntityFilter filter, Pageable pageable);
	
	@Query("SELECT s FROM Lk2TokenServiceEntity s WHERE s.username = ?1 ORDER BY s.id DESC")
	public List<Lk2TokenServiceEntity> findByUsernameOrderByIdDesc(String username);
	
	@Query("SELECT s FROM Lk2TokenServiceEntity s WHERE s.username = ?1 and s.sessionState = ?2 ORDER BY s.id DESC")
	public List<Lk2TokenServiceEntity> findByUsernameAndSessionStateKcOrderByIdDesc(String username, String sessionState);
}
