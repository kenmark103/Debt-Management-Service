package com.projects.auth_service.services;

import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projects.auth_service.dtos.UserDto;
import com.projects.auth_service.exceptions.UserNotFoundException;
import com.projects.auth_service.models.Role;
import com.projects.auth_service.models.User;
import com.projects.auth_service.repositories.RoleRepository;
import com.projects.auth_service.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password, String roleName) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        Role role = roleRepository.findByName(roleName);
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

                return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName())) );
    
    }
    public void updateUser(User user){
        //logic here
    }
    public void deleteUser(User user){
        //logic here
    }
    public UserDto getUserById(UUID userId) {
        // Retrieve the user from the database using the UUID
        User user = userRepository.findById(userId).orElse(null);
        if(user==null){
            throw new UserNotFoundException("User not found with ID: " + userId);
        }
        // Convert the user entity to UserDto to ensure consistency
        return convertToDto(user);
    }

    // Helper method to convert User entity to UserDto
    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());  // Ensures consistency of UUID
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().getName());
        return userDto;
    }
}