package com.aiengineeringpatterns.workflows;

import com.aiengineeringpatterns.dto.TravelRequirements;
import com.aiengineeringpatterns.dto.WorkflowRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class RequirementExtractor {

    public TravelRequirements extract(WorkflowRequest request) {

        return new TravelRequirements(
                request.destination(),
                request.numberOfDays(),
                request.budget(),
                request.preferences()
        );
    }
}
