package com.projects.debt_service.services;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.auth_service.dtos.UserDto;
import com.projects.debt_service.clients.AuthServiceClient;
import com.projects.debt_service.dtos.DebtDto;
import com.projects.debt_service.models.Debt;
import com.projects.debt_service.repositories.DebtRepository;

@Service
public class DebtService {

    @Autowired
    private DebtRepository debtRepository;

    @Autowired
    private AuthServiceClient authServiceClient; // Client to interact with auth service

    // Create a new debt
    public DebtDto createDebt(DebtDto debtDto) {
          // Fetch the user from the Auth Service
    UserDto user = authServiceClient.getUserById(debtDto.getUserId());
    
    if (user == null) {
        throw new IllegalArgumentException("Invalid user ID: User not found");
    }

        Debt debt = new Debt();
        debt.setId(UUID.randomUUID());
        debt.setUserId(user.getId()); // Get ID from the validated user object
        debt.setAmount(debtDto.getAmount());
        debt.setDescription(debtDto.getDescription());
        debt.setSettled(false);
    
    // Save debt in repository
    debtRepository.save(debt);
    
    // Convert saved debt to DTO and return
    return convertToDto(debt);
    }

    // Get debts by user ID
    public List<DebtDto> getDebtsByUserId(UUID userId) {
         // Fetch the user from the Auth Service
    UserDto user = authServiceClient.getUserById(userId);
    
    if (user == null) {
        throw new IllegalArgumentException("Invalid user ID: User not found");
    }

    // Get debts from repository
    List<Debt> debts = debtRepository.findByUserId(user.getId());
    
    // Convert to DTOs if needed
    return debts.stream().map(this::convertToDto).toList();
    }

    // Helper method to convert Debt to DebtDto
    private DebtDto convertToDto(Debt debt) {
        DebtDto dto = new DebtDto();
        dto.setId(debt.getId());
        dto.setUserId(debt.getUserId());
        dto.setAmount(debt.getAmount());
        dto.setDescription(debt.getDescription());
        dto.setSettled(debt.isSettled());
        return dto;
    }
}