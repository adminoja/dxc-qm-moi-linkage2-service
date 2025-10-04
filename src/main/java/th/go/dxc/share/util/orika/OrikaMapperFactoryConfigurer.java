package th.go.dxc.share.util.orika;

import ma.glasnost.orika.MapperFactory;

public interface OrikaMapperFactoryConfigurer {
    void configureMapping(MapperFactory orikaMapperFactory);
}
