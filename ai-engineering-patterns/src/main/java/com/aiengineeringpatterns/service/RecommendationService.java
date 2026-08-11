package com.aiengineeringpatterns.service;

import com.aiengineeringpatterns.dto.RecommendationRequest;
import com.aiengineeringpatterns.dto.RecommendationResponse;
import com.aiengineeringpatterns.model.Product;

public interface RecommendationService {
    RecommendationResponse recommend(RecommendationRequest request);
}
