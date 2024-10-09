package com.projects.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.projects.auth_service.components.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    @SuppressWarnings("unused")
    private final UserDetailsService userService;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsService userService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userService = userService;
    }

    // Bean for PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean for SecurityFilterChain (Replaces WebSecurityConfigurerAdapter)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF protection
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/auth/**").permitAll()  // Permit all auth requests (login/register)
                .requestMatchers("/admin/**").hasRole("ADMIN")  // Only ADMIN can access /admin
                .anyRequest().authenticated()  // All other requests require authentication
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // JWT filter before UsernamePasswordAuthenticationFilter
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Make session stateless
        
        return http.build();
    }

    // Bean for AuthenticationManager (since we aren't extending WebSecurityConfigurerAdapter)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}