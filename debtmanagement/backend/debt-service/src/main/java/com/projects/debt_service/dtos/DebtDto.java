package com.projects.debt_service.dtos;


import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class DebtDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Using UUID
    private UUID id;
    private UUID userId; // reference to user id
    private BigDecimal amount;
    private String description;
    private boolean isSettled;

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
      this.isSettled = b;
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
        return isSettled;
    }
}