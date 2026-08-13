package com.aiengineeringpatterns.controller;

import com.aiengineeringpatterns.dto.WorkflowRequest;
import com.aiengineeringpatterns.dto.WorkflowResponse;
import com.aiengineeringpatterns.workflows.TravelWorkflow;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/workflows")
public class WorkflowController {

    private final TravelWorkflow travelWorkflow;

    public WorkflowController(TravelWorkflow travelWorkflow) {
        this.travelWorkflow = travelWorkflow;
    }

    @PostMapping("/travel")
    public WorkflowResponse executeTravelWorkflow(
            @RequestBody WorkflowRequest request) {

        return travelWorkflow.execute(request);
    }
}
