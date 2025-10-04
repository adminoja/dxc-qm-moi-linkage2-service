package th.go.dxc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import th.go.dxc.share.commons.config.AppInfoConfigurationProperties;

@Profile("dev")
@Configuration
@Slf4j
public class DevApiConfig {
	public DevApiConfig() {
		super();
		log.info("Init {}", DevApiConfig.class.getName());

	}

	@Bean
	public OpenAPI customOpenAPI(AppInfoConfigurationProperties appInfo) {

		return new OpenAPI().components(createOpenApiComponents()).info(createInfo(appInfo));
	}

	private Components createOpenApiComponents() {
		return new Components().addSecuritySchemes("bearerAuth", createSecurityScheme());
	}

	private SecurityScheme createSecurityScheme() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer");
	}

	private Info createInfo(AppInfoConfigurationProperties appInfo) {
		Info info = new Info().title(appInfo.getName() + " [" + appInfo.getProfiles() + "]")
				.description(appInfo.getDescription()).version(appInfo.getBuild().getVersion())
				.license(appInfo.getLicense()).contact(appInfo.getContact()).termsOfService(appInfo.getTermOfService());
		info.addExtension("profile", appInfo.getProfiles());
		return info;
	}

//	@Bean
//	public GroupedOpenApi logApis() { // group all APIs with `user` in the path
//		return GroupedOpenApi.builder().group("log").pathsToMatch("/api/log/**").build();
//	}
//
//	@Bean
//	GroupedOpenApi referenceApis() { // group all APIs with `admin` in the path
//		return GroupedOpenApi.builder().group("reference").pathsToMatch("/api/reference/**").build();
//	}

}
