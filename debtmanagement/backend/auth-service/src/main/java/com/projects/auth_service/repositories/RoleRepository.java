package com.projects.auth_service.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.auth_service.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    Role findByName(String roleName);
}