package th.go.dxc.app.util;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import th.go.dxc.app.model.Lk2ServiceFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntityFilter;

@Mapper
public interface Lk2ServiceFilterToEntityFilterConverter extends Converter<Lk2ServiceFilter, Lk2ServiceEntityFilter> {
	public Lk2ServiceEntityFilter convert(Lk2ServiceFilter source);
}

