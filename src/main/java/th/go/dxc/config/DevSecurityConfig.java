package th.go.dxc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import lombok.extern.slf4j.Slf4j;
import th.go.dxc.share.security.model.DxcUserStatusType;
import th.go.dxc.share.security.util.DxcJwtAuthenticationConverter;

@Profile("dev")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)  
@Slf4j
public class DevSecurityConfig extends WebSecurityConfigurerAdapter {



	public DevSecurityConfig() {
		super();
		log.info("Init {}",DevSecurityConfig.class.getName());
	}

	public DevSecurityConfig(boolean disableDefaults) {
		super(disableDefaults);
		log.info("Init {}",DevSecurityConfig.class.getName());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers(
//				"/favicon.ico"
//				,"/favicon.*"
//				,"/icon.svg"
//				,"/apple-touch-icon.png"
//				,"/manifest.webmanifest"
//				, "/index.html"
//				,"/swagger-ui"
//				,"/swagger-ui/**"
//				, "/actuator/**"
//				, "/v3/api-docs/**"
				"/h2-console/**"
				,"/mock/**"
//				,"/error/**"
				);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//		.csrf().disable()
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//		.and()		
//		.authorizeRequests(authz -> 
//			authz
//			.antMatchers("/api/**").hasRole(DxcUserStatusType.ACCOUNT_ACTIVE.name())
////			.antMatchers("/api/**").authenticated()
//			.anyRequest().permitAll()
//		)
//		.oauth2ResourceServer(oauth2 -> oauth2
//				.jwt()
//				.jwtAuthenticationConverter(new DxcJwtAuthenticationConverter())
//				);
		.cors().and()
		.csrf().disable()
		.requestMatchers().antMatchers("/services/**","/api/**").and()
		.authorizeRequests(authz ->
			authz
			.antMatchers("/services/**/v3/api-docs").permitAll()
			.antMatchers("/api/**").authenticated()
			.anyRequest().permitAll()
		)
		.oauth2ResourceServer(oauth2 -> oauth2
			.jwt()
//			.jwtAuthenticationConverter(new DxcJwtAuthenticationConverter())
		);
	}
}
