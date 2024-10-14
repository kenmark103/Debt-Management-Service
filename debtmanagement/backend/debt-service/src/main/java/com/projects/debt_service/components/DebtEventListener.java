package com.projects.debt_service.components;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.projects.debt_service.events.DebtDefaultedEvent;

@Component
public class DebtEventListener {

    @EventListener
    public void handleDebtDefaultedEvent(DebtDefaultedEvent event) {
        // Logic to handle the event, such as notifying the user or logging
        System.out.println("Debt defaulted: " + event.getDebtId() + " for user: " + event.getUserId());
    }
}