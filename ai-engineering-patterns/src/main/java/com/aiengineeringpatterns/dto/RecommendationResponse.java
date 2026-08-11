package com.aiengineeringpatterns.dto;

import com.aiengineeringpatterns.model.Product;

public record RecommendationResponse(
        Product product,
        String reason,
        double confidence
) {
}