package th.go.dxc.config;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import th.go.dxc.app.service.Linkage2Service;
import th.go.dxc.app.service.Linkage2ServiceImpl;
import th.go.dxc.app.service.Lk2ThaidLogService;
import th.go.dxc.app.service.Lk2ThaidLogServiceImpl;
import th.go.dxc.app.service.Lk2TokenServiceImpl;
import th.go.dxc.app.service.Lk2TokenServiceService;
import th.go.dxc.app.service.LoginLinkage2Service;
import th.go.dxc.app.service.LoginLinkage2ServiceImpl;
import th.go.dxc.app.service.LoginThaidAndLinkage2Service;
import th.go.dxc.app.service.LoginThaidAndLinkage2ServiceImpl;
import th.go.dxc.app.service.LoginThaidService;
import th.go.dxc.app.service.LoginThaidServiceImpl;
import th.go.dxc.app.util.Linkage2ServiceImplMapper;
import th.go.dxc.app.util.Lk2ThaidLogServiceImplMapper;
import th.go.dxc.app.util.Lk2TokenServiceServiceImplMapper;
import th.go.dxc.infra.connector.dopalinkage2.config.DopaLinkage2Properties;
import th.go.dxc.infra.connector.dopalinkage2.service.DopaLinkage2Service;
import th.go.dxc.infra.connector.dopalinkage2.service.DopaLinkage2ServiceWebClientImpl;
import th.go.dxc.infra.connector.thaid.config.ThaidProperties;
import th.go.dxc.infra.connector.thaid.service.ThaidService;
import th.go.dxc.infra.connector.thaid.service.ThaidServiceWebClientImpl;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.repository.Lk2ServiceRepository;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.repository.Lk2ThaidLogRepository;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.repository.Lk2TokenServiceRepository;
import th.go.dxc.infra.datasource.dxcsamdb.useraccount.repository.DxcUserAccountRepository;
import th.go.dxc.share.commons.util.ObjectMapperService;
import th.go.dxc.share.security.service.SecurityService;
import th.go.dxc.share.security.service.SecurityServiceJwtImpl;

@Profile("stg")
@Configuration
@EnableScheduling
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = {"th.go.dxc"})
@Slf4j
public class StgAppConfig {
	public StgAppConfig() {
		super();
		log.info("Init {}",this.getClass().getName());
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
	public LoginThaidService loginThaidService(ThaidService service, MapperFacade mapper, Lk2ThaidLogService lk2ThaidLogService) {
		return new LoginThaidServiceImpl(service, mapper, lk2ThaidLogService);
	}
	
	@Bean
	public Lk2ThaidLogService lk2ThaidLogService(Lk2ThaidLogRepository repository, Lk2ThaidLogServiceImplMapper mapper) {
		return new Lk2ThaidLogServiceImpl(repository, mapper);
	}
	
	@Bean
	public DopaLinkage2Service dopaLinkage2Service(WebClient.Builder webClientBuilder, DopaLinkage2Properties properties) {
		return new DopaLinkage2ServiceWebClientImpl(webClientBuilder, properties);
	}
	
	@Bean
	public LoginLinkage2Service loginLinkage2Service(DopaLinkage2Service service, MapperFacade mapperFacade, 
			Lk2ThaidLogRepository lk2ThaidLogRepository, Lk2TokenServiceService lk2TokenServiceService,
			Linkage2Service linkage2Service, Lk2TokenServiceRepository repository, DxcUserAccountRepository userAccountRepository) {
		return new LoginLinkage2ServiceImpl(service, mapperFacade, lk2ThaidLogRepository, lk2TokenServiceService, linkage2Service, repository
				,userAccountRepository);
	}
	
	@Bean
	public Linkage2Service linkage2Service(DopaLinkage2Service service, MapperFacade mapperFacade, Lk2ServiceRepository repository,
			Linkage2ServiceImplMapper mapper, Lk2TokenServiceRepository lk2TokenServiceRepository, SecurityService securityService,
			@Lazy LoginLinkage2Service loginLinkage2Service, Lk2TokenServiceService lk2TokenServiceService, ObjectMapper objectMapper) {
		return new Linkage2ServiceImpl(service, mapperFacade, repository, mapper, lk2TokenServiceRepository, securityService,
				loginLinkage2Service, lk2TokenServiceService, objectMapper);
	}
	
	@Bean
	public Lk2TokenServiceService lk2TokenServiceService(Lk2TokenServiceRepository repository, Lk2TokenServiceServiceImplMapper mapper) {
		return new Lk2TokenServiceImpl(repository, mapper);
	}
	
	@Bean
	public LoginThaidAndLinkage2Service loginThaidAndLinkage2Service(LoginThaidService loginThaidService, LoginLinkage2Service loginLinkage2Service) {
		return new LoginThaidAndLinkage2ServiceImpl(loginThaidService, loginLinkage2Service);
	}
	
}
