package com.projects.expense_service.events;

import java.math.BigDecimal;
import java.util.UUID;

public class ExpenseAddedEvent {

    private final Long expenseId;
    private final UUID userId;
    private final BigDecimal amount;

    public ExpenseAddedEvent(Long expenseId, UUID userId, BigDecimal amount) {
        this.amount = amount;
        this.expenseId = expenseId;
        this.userId = userId;
    }

    public Long getExpenseId() {
        return expenseId;
    }

    public UUID getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    // toString(), equals(), hashCode() methods
}