package com.projects.debt_service.controllers;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.debt_service.dtos.DebtDto;
import com.projects.debt_service.services.DebtService;

@RestController
@RequestMapping("/api/debts")
public class DebtController {

    @Autowired
    private DebtService debtService;

    // Endpoint to create a new debt
    @PostMapping
    public ResponseEntity<DebtDto> createDebt(@RequestBody DebtDto debtDto) {
        DebtDto createdDebt = debtService.createDebt(debtDto);
        return new ResponseEntity<>(createdDebt, HttpStatus.CREATED);
    }

    // Endpoint to get debts by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DebtDto>> getDebtsByUserId(@PathVariable UUID userId) {
        List<DebtDto> debts = debtService.getDebtsByUserId(userId);
        return new ResponseEntity<>(debts, HttpStatus.OK);
    }
}