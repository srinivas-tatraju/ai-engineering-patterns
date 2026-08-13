package com.aiengineeringpatterns.dto;

public record WorkflowResponse(
        String destination,
        int numberOfDays,
        Itinerary itinerary,
        boolean withinBudget
) {
}
