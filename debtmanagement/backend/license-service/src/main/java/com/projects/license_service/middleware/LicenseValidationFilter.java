package com.projects.license_service.middleware;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.projects.license_service.components.LicenseValidator;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class LicenseValidationFilter implements Filter{
    @Autowired
    private LicenseValidator licenseValidator;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String licenseKey = httpRequest.getHeader("License-Key");

        if (licenseKey == null || !licenseValidator.isValidLicense(licenseKey)) {
            throw new ServletException("Invalid or missing license key");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter initialization logic if needed
    }

    @Override
    public void destroy() {
        // Filter cleanup logic if needed
    }
}
