package com.projects.expense_service.services;

import org.springframework.stereotype.Service;

import com.projects.expense_service.components.BudgetTracker;
import com.projects.expense_service.components.ExpenseValidator;
import com.projects.expense_service.events.BudgetExceededEvent;
import com.projects.expense_service.events.ExpenseAddedEvent;
import com.projects.expense_service.models.Expense;
import com.projects.expense_service.publishers.EventPublisher;
import com.projects.expense_service.repositories.ExpenseRepository;

@Service
public class ExpenseService {

     private final ExpenseRepository expenseRepository;
    private final ExpenseValidator expenseValidator;
    private final BudgetTracker budgetTracker;
    private final EventPublisher eventPublisher;

    public ExpenseService(ExpenseRepository expenseRepository, 
                          ExpenseValidator expenseValidator,
                          BudgetTracker budgetTracker,
                          EventPublisher eventPublisher) {
        this.expenseRepository = expenseRepository;
        this.expenseValidator = expenseValidator;
        this.budgetTracker = budgetTracker;
        this.eventPublisher = eventPublisher;
    }

    public void addExpense(Expense expense) throws Exception {
        
        expenseValidator.validate(expense);
        
        expenseRepository.save(expense);
        
        eventPublisher.publishExpenseAdded(new ExpenseAddedEvent(expense.getId(), expense.getUserId(), expense.getAmount()));
        
        if (budgetTracker.isBudgetExceeded(expense.getUserId(), expense.getAmount())) {
            eventPublisher.publishBudgetExceeded(new BudgetExceededEvent(expense.getUserId(), budgetTracker.getTotalExpenses(expense.getUserId()), budgetTracker.getBudgetLimit(expense.getUserId())));
        }
        
        budgetTracker.updateBudget(expense.getUserId(), expense.getAmount());
    }
}
