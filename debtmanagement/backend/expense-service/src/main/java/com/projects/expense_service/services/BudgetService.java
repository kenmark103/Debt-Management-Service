package com.projects.expense_service.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.projects.expense_service.models.Budget;
import com.projects.expense_service.repositories.BudgetRepository;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    
    @Autowired
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }
    public void setBudget(UUID userId, Double budgetAmount) {
        Budget budget = new Budget(userId, budgetAmount);
        budgetRepository.save(budget); // Save or update budget
    }

    // Get the budget for a user
    public Double getBudget(UUID userId) {
        Optional<Budget> optionalBudget = Optional.ofNullable(budgetRepository.findByUserId(userId));
    
        // Return the budget limit or 0.0 if no budget is found
        return optionalBudget.map(Budget::getLimit).orElse(0.0);
    }

    // Check if the user has a budget set
    public boolean hasBudget(UUID userId) {
        return budgetRepository.findByUserId(userId) != null;
    }

    


}
