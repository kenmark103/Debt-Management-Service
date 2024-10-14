package com.projects.expense_service.components;

import org.springframework.stereotype.Component;

import com.projects.expense_service.config.UserServiceClient;
import com.projects.expense_service.models.Expense;
import com.projects.shared.dtos.UserDto;
import com.projects.shared.exceptions.UserNotFoundException;

@Component
public class ExpenseValidator {

    private final UserServiceClient userServiceClient;

    public ExpenseValidator(UserServiceClient userServiceClient) {
        this.userServiceClient = userServiceClient;
    }

    public void validate(Expense expense) throws UserNotFoundException {
        // Check if user exists via UserService
        UserDto user = userServiceClient.getUserById(expense.getUserId());
        if (user == null) {
            throw new UserNotFoundException("User with ID " + expense.getUserId() + " not found.");
        }
        // Add additional validation logic here if needed (e.g., valid amount, etc.)
    }
}