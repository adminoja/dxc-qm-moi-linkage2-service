package th.go.dxc.config;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;
//import th.go.dxc.share.security.service.ServerLogInterceptor;

@Profile("prd")
@Configuration
@Slf4j
public class PrdWebConfig implements WebMvcConfigurer {
	
	public PrdWebConfig() {
		super();
		log.info("Init {}", this.getClass().getName());
	}
	
//	@Autowired
//	private ServerLogInterceptor serverLogInterceptor;
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(serverLogInterceptor).addPathPatterns("/api/**");
//		WebMvcConfigurer.super.addInterceptors(registry);
//	}
	
}