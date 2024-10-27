package com.projects.license_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projects.license_service.components.LicenseKeyGenerator;
import com.projects.license_service.services.LicenseService;

@RestController
@RequestMapping("/licenses")

public class LicenseController {

    @Autowired
    private LicenseKeyGenerator licenseKeyGenerator;

    @Autowired
    private LicenseService licenseService;

    @PostMapping("/generate")
    public String generateLicense() {
        return licenseService.registerLicense(licenseKeyGenerator.generateLicenseKey());
    }

    @GetMapping("/validate")
    public boolean validateLicense(@RequestParam String username) {
        return licenseService.validateLicense(username);
    }
}