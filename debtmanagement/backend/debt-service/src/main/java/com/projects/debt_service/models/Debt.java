package com.projects.debt_service.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Debt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Using UUID
    private UUID id;
    private UUID userId; // reference to user id
    private BigDecimal amount;
    private BigDecimal interestRate;
    private String description;
    private LocalDate dueDate;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private boolean settled;

    // Getters and Setters

    public void setId(UUID randomUUID) {
        this.id = randomUUID;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setAmount(BigDecimal amount) {
      this.amount = amount;
    }

    public void setSettled(boolean b) {
      this.settled = b;
    }

    public void setDescription(String description) {
       this.description = description;
    }

    public UUID getId() {
       return id;
    }

    public UUID getUserId() {
       return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isSettled() {
        return settled;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
}