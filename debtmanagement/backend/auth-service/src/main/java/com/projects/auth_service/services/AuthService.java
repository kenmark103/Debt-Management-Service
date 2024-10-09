package com.projects.auth_service.services;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
                
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
            .findFirst() 
            .map(GrantedAuthority::getAuthority)
            .orElse("USER_ROLE");
        return jwtTokenProvider.generateToken(userDetails.getUsername(), role);
    }

    public boolean registerUser(String username, String password, String email, String roleName) {
        if (userRepository.findByUsername(username).isPresent()) {
            return false; // User already exists
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.setRole(role);
        userRepository.save(user);
        return true;
    }
}
