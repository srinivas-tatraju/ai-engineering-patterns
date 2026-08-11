package com.aiengineeringpatterns.model;

public record Product(
        String productId,
        String name,
        String category,
        double annualFee,
        String description
) {
}
