package com.projects.debt_service.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.debt_service.models.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID>{
    List<Payment> findByDebtId(UUID debtid);
}
