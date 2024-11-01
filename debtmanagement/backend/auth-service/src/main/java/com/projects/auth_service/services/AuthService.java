package com.projects.auth_service.services;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projects.auth_service.components.JwtTokenProvider;
import com.projects.auth_service.models.Role;
import com.projects.auth_service.models.User;
import com.projects.auth_service.repositories.RoleRepository;
import com.projects.auth_service.repositories.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public AuthService(AuthenticationManager authenticationManager, 
    JwtTokenProvider jwtTokenProvider, UserRepository userRepository, 
    PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public String authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
                
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
            .findFirst() 
            .map(GrantedAuthority::getAuthority)
            .orElse("ROLE_USER");
        return jwtTokenProvider.generateToken(userDetails.getUsername(), role);
    }

    @PostConstruct
    public void init() {

            Role role = new Role();
            role.setId(UUID.randomUUID());
            role.setName("ROLE_ADMIN");
            roleRepository.save(role);

            User user = new User();
            user.setId(UUID.randomUUID());
            user.setUsername("admin");
            user.setEmail("adminSuper@mail.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRole(roleRepository.findByName("ROLE_ADMIN"));
            userRepository.save(user);
    }
}
