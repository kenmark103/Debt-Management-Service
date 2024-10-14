package com.projects.debt_service.services;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.auth_service.dtos.UserDto;
import com.projects.debt_service.clients.AuthServiceClient;
import com.projects.debt_service.dtos.PaymentDto;
import com.projects.debt_service.models.Debt;
import com.projects.debt_service.models.Payment;
import com.projects.debt_service.repositories.DebtRepository;
import com.projects.debt_service.repositories.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final DebtRepository debtRepository;
    private final AuthServiceClient authServiceClient;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, DebtRepository debtRepository, AuthServiceClient authServiceClient) {
        this.paymentRepository = paymentRepository;
        this.debtRepository = debtRepository;
        this.authServiceClient = authServiceClient;
    }

    // Process a new payment for a specific debt
    public PaymentDto processPayment(UUID debtId, PaymentDto paymentDto) {
        // Find the corresponding debt
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new IllegalArgumentException("Debt not found for ID: " + debtId));

        // Create a new payment entry
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID());
        payment.setDebt(debt);  // Link the payment to the debt
        payment.setAmount(paymentDto.getAmount());
        payment.setPaymentDate(paymentDto.getPaymentDate());

        // Save payment
        paymentRepository.save(payment);

        // Update debt balance
        updateDebtBalance(debt, payment.getAmountPaid());

        return convertToDto(payment);  // Return the payment as a DTO
    }

    // Calculate outstanding balance and update the debt
    private void updateDebtBalance(Debt debt, BigDecimal paymentAmount) {
        BigDecimal newBalance = debt.getAmount().subtract(paymentAmount);
        debt.setAmount(newBalance); // Update the debt amount after payment

        // Mark as settled if fully paid
        if (newBalance.compareTo(BigDecimal.ZERO) <= 0) {
            debt.setSettled(true);
        }

        // Save the updated debt
        debtRepository.save(debt);
    }

    // Convert Payment to PaymentDto
    private PaymentDto convertToDto(Payment payment) {
        PaymentDto dto = new PaymentDto();
        dto.setId(payment.getId());
        dto.setDebtId(payment.getDebt().getId());
        dto.setAmount(payment.getAmountPaid());
        dto.setPaymentDate(payment.getPaymentDate());
        return dto;
    }

    public UserDto getUserByPaymentId(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + paymentId));

        // Get the associated debt by debtId
        UUID debtId = payment.getDebt().getId();
        Debt debt = debtRepository.findById(debtId)
                .orElseThrow(() -> new IllegalArgumentException("Debt not found: " + debtId));

        // Now get the user from debt's userId
        UUID userId = debt.getUserId();
        return authServiceClient.getUserById(userId);  // Fetch user details from auth-service
    }
}
