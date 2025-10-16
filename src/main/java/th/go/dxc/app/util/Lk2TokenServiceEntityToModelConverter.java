package th.go.dxc.app.util;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;

import th.go.dxc.app.model.Lk2TokenService;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2TokenServiceEntity;

@Mapper
public interface Lk2TokenServiceEntityToModelConverter extends Converter<Lk2TokenServiceEntity, Lk2TokenService> {
	public Lk2TokenService convert(Lk2TokenServiceEntity source);
}
