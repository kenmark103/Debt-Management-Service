package com.projects.expense_service.publishers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.projects.expense_service.events.BudgetExceededEvent;
import com.projects.expense_service.events.ExpenseAddedEvent;

@Component
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.expenseExchange}")
    private String expenseExchange;

    @Value("${app.rabbitmq.budgetExchange}")
    private String budgetExchange;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishExpenseAdded(ExpenseAddedEvent event) {
        rabbitTemplate.convertAndSend(expenseExchange, "expense.added", event);
    }

    public void publishBudgetExceeded(BudgetExceededEvent event) {
        rabbitTemplate.convertAndSend(budgetExchange, "budget.exceeded", event);
    }
}