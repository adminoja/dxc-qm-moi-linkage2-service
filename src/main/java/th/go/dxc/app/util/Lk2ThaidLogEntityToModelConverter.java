package th.go.dxc.app.util;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import th.go.dxc.app.model.Lk2ThaidLog;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ThaidLogEntity;

@Mapper
public interface Lk2ThaidLogEntityToModelConverter extends Converter<Lk2ThaidLogEntity, Lk2ThaidLog>{
	public Lk2ThaidLog convert(Lk2ThaidLogEntity source);
}
