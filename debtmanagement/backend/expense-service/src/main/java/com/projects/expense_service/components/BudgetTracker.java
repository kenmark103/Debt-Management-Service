package com.projects.expense_service.components;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.projects.expense_service.models.Budget;
import com.projects.expense_service.models.Expense;
import com.projects.expense_service.repositories.BudgetRepository;
import com.projects.expense_service.repositories.ExpenseRepository;

@Component
public class BudgetTracker {

    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;

    @Autowired
    public BudgetTracker(BudgetRepository budgetRepository, ExpenseRepository expenseRepository) {
        this.budgetRepository = budgetRepository;
        this.expenseRepository = expenseRepository;
    }

    public boolean isBudgetExceeded(UUID userId, BigDecimal newExpenseAmount) {
        Budget budget = budgetRepository.findByUserId(userId);
        if (budget == null) {
            // Assuming no budget means no limits
            return false;
        }
        Double totalExpenses = budget.getTotalExpenses() + newExpenseAmount.doubleValue();
        return totalExpenses > budget.getLimit();
   
}
    public void updateBudget(UUID userId, BigDecimal expenseAmount) {
        Budget budget = budgetRepository.findByUserId(userId);
        if (budget != null) {
            budget.setTotalExpenses(budget.getTotalExpenses() + expenseAmount.doubleValue());
            budgetRepository.save(budget);
        }
    }

    public Double getTotalExpenses(UUID userId) {
        List<Expense> userExpenses = expenseRepository.findByUserId(userId);
        BigDecimal totalAmount = userExpenses
                            .stream()
                            .map(Expense::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalAmount.doubleValue();
    }

    public Double getBudgetLimit(UUID userId) {
        Budget budget = budgetRepository.findByUserId(userId);
        return budget.getLimit(); 
    }
}