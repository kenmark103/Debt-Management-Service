package com.projects.expense_service.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.projects.expense_service.events.ExpenseAddedEvent;

@Component
public class ExpenseAddedEventListener {

    @RabbitListener(queues = "${app.rabbitmq.expenseQueue}")
    public void handleExpenseAddedEvent(ExpenseAddedEvent event) {
        // Handle the event (e.g., log, notify, or perform other operations)
        System.out.println("Expense added for user: " + event.getUserId() + " with amount: " + event.getAmount());
    }
}