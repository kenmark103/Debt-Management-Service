package com.projects.expense_service.events;

import java.util.UUID;

public class BudgetExceededEvent {

    private final UUID userId;
    private final Double totalExpenses;
    private final Double budgetLimit;

    public BudgetExceededEvent(UUID userId, Double totalExpenses, Double budgetLimit) {
        this.userId = userId;
        this.totalExpenses = totalExpenses;
        this.budgetLimit = budgetLimit;
    }

    public UUID getUserId() {
        return userId;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public Double getBudgetLimit() {
        return budgetLimit;
    }

    // toString(), equals(), hashCode() methods
}