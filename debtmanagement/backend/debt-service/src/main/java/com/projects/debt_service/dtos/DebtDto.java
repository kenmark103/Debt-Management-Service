package com.projects.debt_service.dtos;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.projects.debt_service.models.Payment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

public class DebtDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Using UUID
    private UUID id;
    private UUID userId; // reference to user id
    private BigDecimal amount;
    private String description;
    private boolean isSettled;
    private boolean defaulted;
    
    @OneToMany(mappedBy = "debt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;

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
    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public boolean isDefaulted() {
        return defaulted;
    }
    public void setDefaulted(Boolean defaulted) {
        this.defaulted = defaulted;
    }
}