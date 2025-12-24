package th.go.dxc.infra.datasource.dxcsamdb.lk2.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ThaidLogEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ThaidLogEntityFilter;

public interface Lk2ThaidLogRepository extends JpaRepository<Lk2ThaidLogEntity, Integer>,
		 JpaSpecificationExecutor<Lk2ThaidLogEntity> {
	@Query(nativeQuery = true, 
			countQuery = "SELECT COUNT(*) FROM " + Lk2ThaidLogEntity.ENTITY_TABLE_NAME + " a "
				+ "where (:#{#filter.id} is null or a.id = :#{#filter.id})"
				+ "and (:#{#filter.username} is null or a.username = :#{#filter.username})"
				+ "and (:#{#filter.accessToken} is null or a.access_token = :#{#filter.accessToken})"
				+ "and (:#{#filter.pid} is null or a.pid = :#{#filter.pid})"
				+ "and (:#{#filter.firstname} is null or a.firstname = :#{#filter.firstname})"
				+ "and (:#{#filter.lastname} is null or a.lastname = :#{#filter.lastname})"
				+ "and (:#{#filter.status} is null or a.status = :#{#filter.status})"
				+ "and (:#{#filter.refreshToken} is null or a.refresh_token = :#{#filter.refreshToken})"
				+ "and (:#{#filter.type} is null or a.type = :#{#filter.type})"
				+ "and (:#{#filter.sessionStateKeycloak} is null or a.session_state_keycloak = :#{#filter.sessionStateKeycloak})"
			, value = "SELECT * FROM " + Lk2ThaidLogEntity.ENTITY_TABLE_NAME + " a "
					+ "where (:#{#filter.id} is null or a.id = :#{#filter.id})"
					+ "and (:#{#filter.username} is null or a.username = :#{#filter.username})"
					+ "and (:#{#filter.accessToken} is null or a.access_token = :#{#filter.accessToken})"
					+ "and (:#{#filter.pid} is null or a.pid = :#{#filter.pid})"
					+ "and (:#{#filter.firstname} is null or a.firstname = :#{#filter.firstname})"
					+ "and (:#{#filter.lastname} is null or a.lastname = :#{#filter.lastname})"
					+ "and (:#{#filter.status} is null or a.status = :#{#filter.status})"
					+ "and (:#{#filter.refreshToken} is null or a.refresh_token = :#{#filter.refreshToken})"
					+ "and (:#{#filter.type} is null or a.type = :#{#filter.type})"
					+ "and (:#{#filter.sessionStateKeycloak} is null or a.session_state_keycloak = :#{#filter.sessionStateKeycloak})"
			)
	public Page<Lk2ThaidLogEntity> findByFilterNative(Lk2ThaidLogEntityFilter filter, Pageable pageable);
	
	@Query("SELECT s FROM Lk2ThaidLogEntity s WHERE s.username = ?1 ORDER BY s.id DESC")
	List<Lk2ThaidLogEntity> findByUsername(String username);
	
//	Lk2ThaidLogEntity findTopByUsernameOrderByIdDesc(String username);
}
