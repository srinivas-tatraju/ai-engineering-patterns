package com.aiengineeringpatterns.model;

public record CustomerProfile(
        String customerId,
        String name,
        double monthlySpending,
        String primarySpendingCategory,
        double maxAnnualFee
) {
}
