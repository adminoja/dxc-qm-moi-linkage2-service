package th.go.dxc.share.config;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory.MapperFactoryBuilder;
import th.go.dxc.share.util.orika.OrikaMapperFactoryBuilderConfigurer;
import th.go.dxc.share.util.orika.OrikaMapperFactoryConfigurer;

@Slf4j
@Configuration
public class OrikaMapperConfig {


    @Bean
    public MapperFactoryBuilder<?, ?> orikaMapperFactoryBuilder(Optional<List<OrikaMapperFactoryBuilderConfigurer>> orikaMapperFactoryBuilderConfigurers) {
        DefaultMapperFactory.Builder orikaMapperFactoryBuilder = new DefaultMapperFactory.Builder();
//        if (orikaProperties.getUseBuiltinConverters() != null) {
//            orikaMapperFactoryBuilder.useBuiltinConverters(orikaProperties.getUseBuiltinConverters());
//        }
//        if (orikaProperties.getUseAutoMapping() != null) {
//            orikaMapperFactoryBuilder.useAutoMapping(orikaProperties.getUseAutoMapping());
//        }
//        if (orikaProperties.getMapNulls() != null) {
//            orikaMapperFactoryBuilder.mapNulls(orikaProperties.getMapNulls());
//        }
//        if (orikaProperties.getDumpStateOnException() != null) {
//            orikaMapperFactoryBuilder.dumpStateOnException(orikaProperties.getDumpStateOnException());
//        }
//        if (orikaProperties.getFavorExtension() != null) {
//            orikaMapperFactoryBuilder.favorExtension(orikaProperties.getFavorExtension());
//        }
//        if (orikaProperties.getCaptureFieldContext() != null) {
//            orikaMapperFactoryBuilder.captureFieldContext(orikaProperties.getCaptureFieldContext());
//        }
        orikaMapperFactoryBuilderConfigurers
                .orElseGet(Collections::emptyList)
                .forEach(configurer -> configurer.configureFactoryBuilder(orikaMapperFactoryBuilder));
        log.debug("Created a MapperFactoryBuilder: [{}]", orikaMapperFactoryBuilder);
        return orikaMapperFactoryBuilder;
    }
//	@Primary
//	@Bean
//	public MapperFactory mapperFactory(Optional<List<OrikaMapperFactoryBuilderConfigurer>> orikaMapperFactoryBuilderConfigurers) {
//		MapperFactory mapperFactory =  new DefaultMapperFactory.Builder().build();
//		return mapperFactory;
//	}
	@Primary
    @Bean
    public MapperFactory orikaMapperFactory(MapperFactoryBuilder<?, ?> orikaMapperFactoryBuilder,Optional<List<OrikaMapperFactoryConfigurer>> orikaMapperFactoryConfigurers) {
        MapperFactory orikaMapperFactory = orikaMapperFactoryBuilder.build();
        orikaMapperFactoryConfigurers
                .orElseGet(Collections::emptyList)
                .forEach(configurer -> configurer.configureMapping(orikaMapperFactory));
        log.debug("Created a MapperFactory: [{}]", orikaMapperFactory);
        return orikaMapperFactory;
    }

	@Primary
    @Bean
    public MapperFacade orikaMapperFacade(MapperFactory orikaMapperFactory) {
        MapperFacade orikaMapperFacade = orikaMapperFactory.getMapperFacade();
        log.debug("Created a MapperFacade: [{}]", orikaMapperFacade);
        return orikaMapperFacade;
    }

}
