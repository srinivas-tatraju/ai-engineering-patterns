package com.aiengineeringpatterns.dto;

public record TransactionEvent(
        String transactionId,
        String customerId,
        double amount,
        String category
) {
}
