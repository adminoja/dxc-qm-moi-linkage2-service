package th.go.dxc.app.util;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import th.go.dxc.app.model.Lk2TokenServiceFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2TokenServiceEntityFilter;

@Mapper
public interface Lk2TokenServiceFilterToEntityFilterConverter extends Converter<Lk2TokenServiceFilter, Lk2TokenServiceEntityFilter> {
	public Lk2TokenServiceEntityFilter convert(Lk2TokenServiceFilter source);
}
