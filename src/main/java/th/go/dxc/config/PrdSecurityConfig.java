package th.go.dxc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;

import lombok.extern.slf4j.Slf4j;
import th.go.dxc.share.security.model.DxcUserStatusType;
import th.go.dxc.share.security.util.DxcJwtAuthenticationConverter;

@Profile("prd")
@Configuration
@Slf4j
public class PrdSecurityConfig extends WebSecurityConfigurerAdapter {
	
	public PrdSecurityConfig() {
		super();
		log.info("Init {}",this.getClass().getName());	}

	public PrdSecurityConfig(boolean disableDefaults) {
		super(disableDefaults);
		log.info("Init {}",this.getClass().getName());
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
		.cors().and()
		.csrf().disable()
		.requestMatchers().antMatchers("/services/**","/api/**").and()
		.authorizeRequests(authz ->
			authz
			.antMatchers("/services/**/v2/api-docs").permitAll()
			.antMatchers("/api/**").authenticated()
			.anyRequest().permitAll()
		)
		.oauth2ResourceServer(oauth2 -> oauth2
			.jwt()
		);
	}
	
//	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("dxc-search-server")
////                .secret("{noop}your-client-secret")
//                .authorizedGrantTypes("refresh_token")
////                .scopes("read", "write")
//                .accessTokenValiditySeconds(30) // อายุของ Access Token 1 ชั่วโมง
//                .refreshTokenValiditySeconds(86400); // อายุของ Refresh Token 1 วัน
//    }
}
