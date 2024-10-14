package com.projects.expense_service.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExpenseDto {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
    // Optionally add userName if required
    // Getters, setters, and constructors
}