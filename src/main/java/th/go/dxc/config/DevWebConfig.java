package th.go.dxc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Profile("dev")
@Configuration
@Slf4j
public class DevWebConfig implements WebMvcConfigurer{
	
	public DevWebConfig() {
		super();
		log.info("Init {}",this.getClass().getName());
	}

}