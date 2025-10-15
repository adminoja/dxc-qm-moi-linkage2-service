package th.go.dxc.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import th.go.dxc.app.model.Lk2TokenService;
import th.go.dxc.app.model.Lk2TokenServiceFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2TokenServiceEntity;

public interface Lk2TokenServiceService {

	Page<Lk2TokenService> findAll(Lk2TokenServiceFilter filter, Pageable pageable);

	Lk2TokenServiceEntity insert(Lk2TokenService lk2TokenService);

}
