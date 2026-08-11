package com.aiengineeringpatterns.service.impl;

import com.aiengineeringpatterns.dto.RecommendationDecision;
import com.aiengineeringpatterns.dto.RecommendationRequest;
import com.aiengineeringpatterns.dto.RecommendationResponse;
import com.aiengineeringpatterns.model.Product;
import com.aiengineeringpatterns.service.ProductCatalog;
import com.aiengineeringpatterns.service.RecommendationService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final ProductCatalog productCatalog;
    private final ChatClient chatClient;

    public RecommendationServiceImpl(
            ProductCatalog productCatalog,
            ChatClient chatClient) {
        this.productCatalog = productCatalog;
        this.chatClient = chatClient;
    }

    @Override
    public RecommendationResponse recommend(RecommendationRequest request) {
        List<Product> products = productCatalog.getRelevantProducts(request.customerProfile());
        if (products.isEmpty()) {
            throw new IllegalStateException("No suitable products found for the customer");
        }

        RecommendationDecision decision = chatClient.prompt()
                .user("""
                        You are a product recommendation assistant.
                        
                        Customer profile:
                        %s
                        
                        Customer requirement:
                        %s
                        
                        Available products:
                        %s
                        
                        Evaluate the products using the following priorities:
                        
                        1. Match the customer's primary spending category.
                        2. Match the customer's stated requirements.
                        3. Do not recommend a product whose annual fee exceeds the customer's maximum annual fee.
                        4. Among suitable products, select the best overall match.
                        
                        Select exactly one product.
                        
                        The productId MUST be one of the product IDs provided above.
                        
                        Return:
                        - productId
                        - reason
                        - confidence
                        """.formatted(
                        request.customerProfile(),
                        request.requirement(),
                        products
                ))
                .call()
                .entity(RecommendationDecision.class);

        Product recommendedProduct = products.stream()
                .filter(product -> product.productId().equals(decision.productId()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("AI returned an invalid product ID: " + decision.productId()));

        return new RecommendationResponse(
                recommendedProduct,
                decision.reason(),
                decision.confidence()
        );
    }
}