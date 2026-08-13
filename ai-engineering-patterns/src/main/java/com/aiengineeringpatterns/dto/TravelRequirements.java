package com.aiengineeringpatterns.dto;

public record TravelRequirements(
        String destination,
        int numberOfDays,
        double budget,
        String preferences
) {
}
