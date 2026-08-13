package com.aiengineeringpatterns.dto;

import java.util.List;

public record Itinerary(
        String destination,
        List<String> dailyPlans
) {
}
