package com.projects.debt_service.events;

import java.util.UUID;

public class DebtCreatedEvent {
    private final UUID debtId;
    private final UUID userId;

    public DebtCreatedEvent(UUID debtId, UUID userId) {
        this.debtId = debtId;
        this.userId = userId;
    }

    public UUID getDebtId() {
        return debtId;
    }

    public UUID getUserId() {
        return userId;
    }
}