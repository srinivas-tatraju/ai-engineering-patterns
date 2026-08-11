package com.aiengineeringpatterns.controller;

import com.aiengineeringpatterns.dto.RecommendationRequest;
import com.aiengineeringpatterns.dto.RecommendationResponse;
import com.aiengineeringpatterns.model.Product;
import com.aiengineeringpatterns.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping
    public RecommendationResponse recommend(@RequestBody RecommendationRequest request) {
        return recommendationService.recommend(request);
    }
}