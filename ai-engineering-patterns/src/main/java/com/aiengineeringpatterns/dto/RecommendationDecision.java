package com.aiengineeringpatterns.dto;

public record RecommendationDecision(
        String productId,
        String reason,
        double confidence
) {
}
