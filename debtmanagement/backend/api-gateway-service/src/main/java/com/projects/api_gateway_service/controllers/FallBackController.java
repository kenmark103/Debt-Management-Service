package com.projects.api_gateway_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @RequestMapping("/fallback/auth")
    public String authServiceFallback() {
        return "Auth Service is currently unavailable. Please try again later.";
    }

    @RequestMapping("/fallback/debt")
    public String debtServiceFallback() {
        return "Debt Service is currently unavailable. Please try again later.";
    }

    @RequestMapping("/fallback/payment")
    public String paymentServiceFallback() {
        return "Payment Service is currently unavailable. Please try again later.";
    }
}
