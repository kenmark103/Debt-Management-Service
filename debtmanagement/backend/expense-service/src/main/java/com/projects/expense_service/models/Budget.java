package com.projects.expense_service.models;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID userId;        // ID of the user associated with the budget
    private Double limit;      // Budget limit for the user
    private Double totalExpenses; // Total expenses made by the user

    public Budget() {
        this.totalExpenses = 0.0; // Initialize total expenses to 0
    }

    public Budget(UUID userId, Double limit) {
        this.userId = userId;
        this.limit = limit;
        this.totalExpenses = 0.0;
    }

    public Long getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public Double getLimit() {
        return limit;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public void addExpense(Double amount) {
        this.totalExpenses += amount; // Increment total expenses
    }
}