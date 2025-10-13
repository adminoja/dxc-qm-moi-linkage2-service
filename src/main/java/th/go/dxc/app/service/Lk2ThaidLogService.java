package th.go.dxc.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import th.go.dxc.app.model.Lk2ThaidLog;
import th.go.dxc.app.model.Lk2ThaidLogFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ThaidLogEntity;

public interface Lk2ThaidLogService {

	Page<Lk2ThaidLog> findAll(Lk2ThaidLogFilter filter, Pageable pageable);

	Lk2ThaidLogEntity insert(Lk2ThaidLog lk2ThaidLog);

}
