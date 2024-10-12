package com.projects.debt_service.controllers;

import com.projects.debt_service.dtos.PaymentDto;
import com.projects.debt_service.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Endpoint to process payment for a debt
    @PostMapping("/{debtId}")
    public ResponseEntity<PaymentDto> processPayment(@PathVariable UUID debtId, @RequestBody PaymentDto paymentDto) {
        PaymentDto processedPayment = paymentService.processPayment(debtId, paymentDto);
        return ResponseEntity.ok(processedPayment);
    }
}