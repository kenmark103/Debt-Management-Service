package com.projects.debt_service.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.projects.debt_service.components.EventPublisher;
import com.projects.debt_service.events.DebtDefaultedEvent;
import com.projects.debt_service.models.Debt;
import com.projects.debt_service.repositories.DebtRepository;

@Service
public class DebtCheckService {

    private final DebtRepository debtRepository;
    private final EventPublisher eventPublisher; // Your event publisher

    public DebtCheckService(DebtRepository debtRepository, EventPublisher eventPublisher) {
        this.debtRepository = debtRepository;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedRate = 86400000) // Run once a day
    public void checkForDefaultedDebts() {
        LocalDateTime now = LocalDateTime.now();
        List<Debt> overdueDebts = debtRepository.findByDueDateBeforeAndDefaulted(now, false);

        for (Debt debt : overdueDebts) {
            // Publish the DebtDefaultedEvent
            DebtDefaultedEvent event = new DebtDefaultedEvent(debt.getId(), debt.getUserId(), now);
            eventPublisher.publish(event);

            // Optionally, update the debt status to indicate it has defaulted
            debt.setDefaulted(true);
            debtRepository.save(debt);
        }
    }
}