package com.aiengineeringpatterns.dto;

public record TransactionRiskDecision(
        String transactionId,
        String riskLevel,
        String reason
) {
}
