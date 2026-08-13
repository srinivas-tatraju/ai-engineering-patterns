package com.aiengineeringpatterns.workflows;

import com.aiengineeringpatterns.dto.TravelRequirements;
import org.springframework.stereotype.Service;

@Service
public class BudgetValidator {

    private static final double ESTIMATED_DAILY_COST = 300.0;

    public boolean isWithinBudget(TravelRequirements requirements) {
        double estimatedCost = requirements.numberOfDays() * ESTIMATED_DAILY_COST;
        return estimatedCost <= requirements.budget();
    }
}