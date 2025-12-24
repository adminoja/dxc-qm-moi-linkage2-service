package th.go.dxc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;
import th.go.dxc.share.security.model.DxcUserStatusType;
import th.go.dxc.share.security.util.DxcJwtAuthenticationConverter;

@Profile("prd")
@Configuration
@EnableWebSecurity
@Slf4j
public class PrdSecurityConfig {

  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    // Keep equivalent behavior to your old web.ignoring().antMatchers(...)
    // WARNING: in production, consider removing these.
    return web -> web.ignoring().requestMatchers(
        "/h2-console/**",
        "/mock/**"
    );
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)

        .authorizeHttpRequests(auth -> auth
            // Safer matcher set (works with typical OpenAPI endpoints)
            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

            // Your original intent:
            // - /api/** requires auth + role
            .requestMatchers("/api/**")
              .hasRole(DxcUserStatusType.ACCOUNT_ACTIVE.name())

            .anyRequest().permitAll()
        )

        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt
                .jwtAuthenticationConverter(new DxcJwtAuthenticationConverter())
            )
        );

    return http.build();
  }
}
