package th.go.dxc.app.util;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import th.go.dxc.app.model.Lk2TokenService;
import th.go.dxc.app.model.Lk2TokenServiceFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2TokenServiceEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2TokenServiceEntityFilter;
import th.go.dxc.share.commons.util.ObjectMapperService;

@Component
public class Lk2TokenServiceServiceImplMapper {
	
	private final ObjectMapperService mapper;
	
	public Lk2TokenServiceServiceImplMapper(ObjectMapperService mapper) {
		super();
		this.mapper = mapper;
	}
	
	public Pageable mapEntityPageable(Pageable pageable) {
		return pageable;
	}
	
	public Page<Lk2TokenService> mapModelPage(Page<Lk2TokenServiceEntity> entityPage) {
		return mapper.mapPage(entityPage, Lk2TokenService.class);
	}
	
	public Lk2TokenServiceEntityFilter mapEntityFilter(Lk2TokenServiceFilter filter) {
		return mapper.map(filter, Lk2TokenServiceEntityFilter.class);
	}
	
	public List<Lk2TokenService> mapAsList(List<Lk2TokenServiceEntity> entityList) {
		return mapper.mapAsList(entityList, Lk2TokenService.class);
	}
}
