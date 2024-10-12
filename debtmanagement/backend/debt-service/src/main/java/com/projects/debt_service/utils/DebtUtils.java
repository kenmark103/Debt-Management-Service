package com.projects.debt_service.utils;

import java.math.BigDecimal;

public class DebtUtils {
    public static BigDecimal calculateRemainingBalance(BigDecimal totalAmount, BigDecimal paidAmount) {
    return totalAmount.subtract(paidAmount);
}

}
