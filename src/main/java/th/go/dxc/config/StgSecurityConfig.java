package th.go.dxc.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Profile("stg")
@Configuration
@EnableWebSecurity
@Slf4j
public class StgSecurityConfig {

  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    // Equivalent to old web.ignoring().antMatchers(...)
    return web -> web.ignoring().requestMatchers(
        "/h2-console/**",
        "/mock/**"
    );
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        .authorizeHttpRequests(auth -> auth
            // If you want swagger in stg, uncomment:
            // .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

            .requestMatchers("/api/**").authenticated()
            .anyRequest().permitAll()
        )

        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        // If you want the converter in stg:
        // .oauth2ResourceServer(oauth2 -> oauth2
        //     .jwt(jwt -> jwt.jwtAuthenticationConverter(new DxcJwtAuthenticationConverter()))
        // );

    return http.build();
  }
}
