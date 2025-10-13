package th.go.dxc.app.util;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import th.go.dxc.app.model.Lk2ThaidLogFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ThaidLogEntityFilter;

@Mapper
public interface Lk2ThaidLogFilterToEntityFilterConverter extends Converter<Lk2ThaidLogFilter, Lk2ThaidLogEntityFilter> {
	public Lk2ThaidLogEntityFilter convert(Lk2ThaidLogFilter source);
}
