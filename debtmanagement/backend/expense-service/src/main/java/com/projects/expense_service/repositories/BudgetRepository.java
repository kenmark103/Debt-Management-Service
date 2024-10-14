package com.projects.expense_service.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.expense_service.models.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long>{
    Budget findByUserId(UUID userId);
}
