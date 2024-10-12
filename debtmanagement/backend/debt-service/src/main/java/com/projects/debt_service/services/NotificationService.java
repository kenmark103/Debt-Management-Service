package com.projects.debt_service.services;

import com.projects.debt_service.events.DebtCreatedEvent;
import com.projects.debt_service.events.PaymentMadeEvent;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendDebtCreationNotification(DebtCreatedEvent event) {
        // Logic to notify the user about the created debt
        System.out.println("Notification sent for created debt: " + event.getDebtId());
    }

    public void sendPaymentNotification(PaymentMadeEvent event) {
        // Logic to notify the user about the payment made
        System.out.println("Notification sent for payment made: " + event.getPaymentId());
    }
}
