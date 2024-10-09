package com.projects.license_service.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.license_service.models.License;
import com.projects.license_service.repositories.LicenseRepository;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    // Register a new license with a generated license key
    public String registerLicense(String licenseKey) {
        License license = new License();
        license.setLicenseKey(licenseKey);
        license.setCreationDate(LocalDateTime.now());
        license.setExpirationDate(LocalDateTime.now().plusYears(1));  // License expires in 1 year
        license.setActive(true);
        
        licenseRepository.save(license);
        return "License registered successfully with key: " + licenseKey;
    }

    // Validate if a given license key is valid
    public boolean validateLicense(String licenseKey) {
        Optional<License> optionalLicense = Optional.ofNullable(licenseRepository.findByLicenseKey(licenseKey));
        if (optionalLicense.isPresent()) {
            License license = optionalLicense.get();
            return license.isActive() && !license.isExpired();
        }
        return false;
    }

    // Deactivate a license
    public String deactivateLicense(String licenseKey) {
        Optional<License> optionalLicense = Optional.ofNullable(licenseRepository.findByLicenseKey(licenseKey));
        if (optionalLicense.isPresent()) {
            License license = optionalLicense.get();
            license.setActive(false);
            licenseRepository.save(license);
            return "License deactivated successfully.";
        }
        return "License not found.";
    }

    // Check license details
    public License getLicenseDetails(String licenseKey) {
        return licenseRepository.findByLicenseKey(licenseKey);
    }

    // Extend the license expiration
    public String extendLicense(String licenseKey, int additionalYears) {
        Optional<License> optionalLicense = Optional.ofNullable(licenseRepository.findByLicenseKey(licenseKey));
        if (optionalLicense.isPresent()) {
            License license = optionalLicense.get();
            license.setExpirationDate(license.getExpirationDate().plusYears(additionalYears));
            licenseRepository.save(license);
            return "License extended for " + additionalYears + " years.";
        }
        return "License not found.";
    }
}