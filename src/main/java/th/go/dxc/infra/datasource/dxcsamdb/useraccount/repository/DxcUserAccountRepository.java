package th.go.dxc.infra.datasource.dxcsamdb.useraccount.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import th.go.dxc.infra.datasource.dxcsamdb.useraccount.entity.DxcUserAccountEntity;


@Repository
public interface DxcUserAccountRepository extends PagingAndSortingRepository<DxcUserAccountEntity, Long>{
	
	@Query(value = "select a from DxcUserAccountEntity a where a.id = ?1")
	public List<DxcUserAccountEntity> filterById(Integer id);

	@Query(value = "select a from DxcUserAccountEntity a where a.id = ?1")
	public DxcUserAccountEntity findByIdUpdate(Integer id);
	
	@Query
	(value = "SELECT u FROM DxcUserAccountEntity u WHERE u.username = ?1" )
	List<DxcUserAccountEntity> findByUsername(String username);
	
	@Query
	(value = "SELECT u FROM DxcUserAccountEntity u WHERE u.citizenCardNumber = ?1" )
	List<DxcUserAccountEntity> findByCitizenCardNumber(String citizenCardNumber);
}
