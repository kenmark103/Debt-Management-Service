package com.projects.debt_service.repositories;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projects.debt_service.models.Debt;

public interface DebtRepository extends JpaRepository<Debt, UUID> {
    List<Debt> findByUserId(UUID userId); // Find debts by user ID
    List<Debt> findByDueDateBeforeAndDefaulted(LocalDateTime dueDate, boolean defaulted);
}