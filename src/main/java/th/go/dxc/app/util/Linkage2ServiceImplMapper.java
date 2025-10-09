package th.go.dxc.app.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import th.go.dxc.app.model.Lk2Service;
import th.go.dxc.app.model.Lk2ServiceFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntityFilter;
import th.go.dxc.share.commons.util.ObjectMapperService;

@Component
public class Linkage2ServiceImplMapper {
	private final ObjectMapperService mapper;
	
	public Linkage2ServiceImplMapper(ObjectMapperService mapper) {
		super();
		this.mapper = mapper;
	}
	
	public Pageable mapEntityPageable(Pageable pageable) {
		return pageable;
	}
	
	public Page<Lk2Service> mapModelPage(Page<Lk2ServiceEntity> entityPage) {
		return mapper.mapPage(entityPage, Lk2Service.class);
	}
	
	public Lk2ServiceEntityFilter mapEntityFilter(Lk2ServiceFilter filter) {
		return mapper.map(filter, Lk2ServiceEntityFilter.class);
	}
	
	public List<Lk2Service> mapAsList(List<Lk2ServiceEntity> entityList) {
		return mapper.mapAsList(entityList, Lk2Service.class);
	}
}
