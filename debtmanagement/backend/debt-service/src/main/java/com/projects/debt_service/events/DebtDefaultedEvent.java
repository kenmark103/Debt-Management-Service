package com.projects.debt_service.events;

import java.time.LocalDateTime;
import java.util.UUID;

public class DebtDefaultedEvent {
    private final UUID debtId;
    private final UUID userId;
    private final LocalDateTime defaultDate;

    // Constructors, getters, and setters

    public DebtDefaultedEvent(UUID debtId, UUID userId, LocalDateTime now) {
        this.debtId = debtId;
        this.defaultDate = now;
        this.userId = userId;
    }
    public UUID getUserId() {
        return userId;
    }
    public LocalDateTime getDefaultDate() {
        return defaultDate;
    }

    public UUID getDebtId() {
        return debtId;
    }
}
