package com.aiengineeringpatterns.dto;


public record WorkflowRequest(
        String destination,
        int numberOfDays,
        double budget,
        String preferences
) {
}