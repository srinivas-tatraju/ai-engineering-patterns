package com.aiengineeringpatterns.dto;

import com.aiengineeringpatterns.model.CustomerProfile;

public record RecommendationRequest(
        String customerId,
        CustomerProfile customerProfile,
        String requirement
) {
}