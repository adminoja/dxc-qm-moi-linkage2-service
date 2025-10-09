package th.go.dxc.app.util;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import th.go.dxc.app.model.Lk2Service;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntity;

@Mapper
public interface Lk2ServiceEntityToModelConverter extends Converter<Lk2ServiceEntity, Lk2Service> {
	public Lk2Service convert(Lk2ServiceEntity source);
}

