package com.projects.api_gateway_service.config;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
            .csrf().disable()
            .authorizeExchange()
                .pathMatchers("/eureka/**").permitAll()  // Allow Eureka endpoints
                // ... other security rules ...
            .anyExchange().authenticated()
            .and()
            .http
