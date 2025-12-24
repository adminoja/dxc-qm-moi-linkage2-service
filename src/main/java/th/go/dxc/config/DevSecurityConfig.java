package th.go.dxc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Profile("dev")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Slf4j
public class DevSecurityConfig {

  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    // Bypass Spring Security filter chain entirely for these (dev-only) paths
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

        // Needed for H2 console (frames)
        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/services/*/v3/api-docs", "/services/*/v3/api-docs/**").permitAll()
            .requestMatchers("/api/**").authenticated()
            .anyRequest().permitAll()
        )

        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        // If you have custom converter:
        // .oauth2ResourceServer(oauth2 -> oauth2
        //     .jwt(jwt -> jwt.jwtAuthenticationConverter(new DxcJwtAuthenticationConverter()))
        // );

    return http.build();
  }
}
