package th.go.dxc.app.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import th.go.dxc.app.model.Lk2Service;
import th.go.dxc.app.model.Lk2ServiceFilter;
import th.go.dxc.app.model.Lk2ThaidLog;
import th.go.dxc.app.model.Lk2ThaidLogFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntityFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ThaidLogEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ThaidLogEntityFilter;
import th.go.dxc.share.commons.util.ObjectMapperService;

@Component
public class Lk2ThaidLogServiceImplMapper {
	
	private final ObjectMapperService mapper;
	
	public Lk2ThaidLogServiceImplMapper(ObjectMapperService mapper) {
		super();
		this.mapper = mapper;
	}
	
	public Pageable mapEntityPageable(Pageable pageable) {
		return pageable;
	}
	
	public Page<Lk2ThaidLog> mapModelPage(Page<Lk2ThaidLogEntity> entityPage) {
		return mapper.mapPage(entityPage, Lk2ThaidLog.class);
	}
	
	public Lk2ThaidLogEntityFilter mapEntityFilter(Lk2ThaidLogFilter filter) {
		return mapper.map(filter, Lk2ThaidLogEntityFilter.class);
	}
	
	public List<Lk2ThaidLog> mapAsList(List<Lk2ThaidLogEntity> entityList) {
		return mapper.mapAsList(entityList, Lk2ThaidLog.class);
	}
}
