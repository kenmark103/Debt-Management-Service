package com.projects.expense_service.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.projects.expense_service.events.BudgetExceededEvent;

@Component
public class BudgetExceededEventListener {

    @RabbitListener(queues = "${app.rabbitmq.budgetQueue}")
    public void handleBudgetExceededEvent(BudgetExceededEvent event) {
        // Handle the event (e.g., notify user, log, etc.)
        System.out.println("User " + event.getUserId() + " has exceeded their budget.");
    }
}