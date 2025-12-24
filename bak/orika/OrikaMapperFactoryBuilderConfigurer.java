package th.go.dxc.share.util.orika;

import ma.glasnost.orika.impl.DefaultMapperFactory.MapperFactoryBuilder;

public interface OrikaMapperFactoryBuilderConfigurer {
    void configureFactoryBuilder(MapperFactoryBuilder<?,?> orikaMapperFactoryBuilder);
}
