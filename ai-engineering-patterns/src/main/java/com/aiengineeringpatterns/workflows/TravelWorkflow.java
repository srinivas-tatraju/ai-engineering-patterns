package com.aiengineeringpatterns.workflows;

import com.aiengineeringpatterns.dto.Itinerary;
import com.aiengineeringpatterns.dto.TravelRequirements;
import com.aiengineeringpatterns.dto.WorkflowRequest;
import com.aiengineeringpatterns.dto.WorkflowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TravelWorkflow {

    private final RequirementExtractor requirementExtractor;

    private final ItineraryGenerator itineraryGenerator;

    private final BudgetValidator budgetValidator;

    public WorkflowResponse execute(WorkflowRequest request) {
        TravelRequirements requirements = requirementExtractor.extract(request);
        Itinerary itinerary = itineraryGenerator.generate(requirements);
        boolean withinBudget = budgetValidator.isWithinBudget(requirements);
        return new WorkflowResponse(
                requirements.destination(),
                requirements.numberOfDays(),
                itinerary,
                withinBudget
        );
    }
}