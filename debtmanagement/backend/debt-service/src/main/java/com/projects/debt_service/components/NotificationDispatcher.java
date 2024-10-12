package com.projects.debt_service.components;

import com.projects.debt_service.events.DebtCreatedEvent;
import com.projects.debt_service.events.PaymentMadeEvent;
import com.projects.debt_service.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationDispatcher {

    @Autowired
    private NotificationService notificationService;

    public void dispatchDebtCreationNotification(DebtCreatedEvent event) {
        notificationService.sendDebtCreationNotification(event);
    }

    public void dispatchPaymentNotification(PaymentMadeEvent event) {
        notificationService.sendPaymentNotification(event);
    }
}