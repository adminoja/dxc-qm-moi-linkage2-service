package th.go.dxc.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import th.go.dxc.app.service.Linkage2Service;
import th.go.dxc.app.service.Linkage2ServiceImpl;
import th.go.dxc.app.service.LoginThaidService;
import th.go.dxc.app.service.LoginThaidServiceImpl;
import th.go.dxc.infra.connector.dopalinkage2.config.DopaLinkage2Properties;
import th.go.dxc.infra.connector.dopalinkage2.service.DopaLinkage2Service;
import th.go.dxc.infra.connector.dopalinkage2.service.DopaLinkage2ServiceWebClientImpl;
import th.go.dxc.infra.connector.thaid.config.ThaidProperties;
import th.go.dxc.infra.connector.thaid.service.ThaidService;
import th.go.dxc.infra.connector.thaid.service.ThaidServiceWebClientImpl;
import th.go.dxc.share.commons.util.ObjectMapperService;
import th.go.dxc.share.security.service.SecurityService;
import th.go.dxc.share.security.service.SecurityServiceJwtImpl;

@Profile("dev")
@Configuration
@EnableScheduling
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = { "th.go.dxc" })
@Slf4j
public class DevAppConfig {
	public DevAppConfig() {
		super();
		log.info("Init {}", DevAppConfig.class.getName());
	}

	@Bean
	public MapperFactory mapperFactory() {
		return new DefaultMapperFactory.Builder().build();
	}

	@Bean
	public SecurityService securityService() {
		return new SecurityServiceJwtImpl(); 
	}
	
	@Bean
	public ThaidService thaidService(WebClient.Builder webClientBuilder, ThaidProperties properties) {
		return new ThaidServiceWebClientImpl(webClientBuilder, properties);
	}
	
	@Bean
	public LoginThaidService loginThaidService(ThaidService service, MapperFacade mapper) {
		return new LoginThaidServiceImpl(service, mapper);
	}
	
	@Bean
	public DopaLinkage2Service dopaLinkage2Service(WebClient.Builder webClientBuilder, DopaLinkage2Properties properties) {
		return new DopaLinkage2ServiceWebClientImpl(webClientBuilder, properties);
	}
	
	@Bean
	public Linkage2Service linkage2Service(DopaLinkage2Service service, MapperFacade mapper) {
		return new Linkage2ServiceImpl(service, mapper);
	}
	
}
