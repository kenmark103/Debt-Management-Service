package com.projects.debt_service.events;

import java.util.UUID;

public class PaymentMadeEvent {
    private final UUID paymentId;
    private final UUID debtId;

    public PaymentMadeEvent(UUID paymentId, UUID debtId) {
        this.paymentId = paymentId;
        this.debtId = debtId;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public UUID getDebtId() {
        return debtId;
    }
}