package com.projects.license_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.license_service.models.License;

public interface  LicenseRepository extends JpaRepository<License, Long> {
    License findByLicenseKey(String licenseKey);

    License findByAssignedTo(String username);
}
