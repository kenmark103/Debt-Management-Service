package com.projects.api_gateway_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection
            .authorizeExchange(exchange -> exchange
                .pathMatchers("/public/**", "/favicon.ico").permitAll()
                .pathMatchers("/eureka/**").permitAll()  // Allow Eureka endpoints
                .pathMatchers("/auth/**").permitAll()
                .pathMatchers("/actuator/**").hasRole("ADMIN")
                .anyExchange().authenticated()  // All other requests require authentication
            )
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance()) // Set stateless
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp.policyDirectives("frame-ancestors 'self'")) // Allow frames from same origin for H2 console
            )
            .formLogin(login -> login.disable()) // Disable form login using lambda
                .build();
    }
}
