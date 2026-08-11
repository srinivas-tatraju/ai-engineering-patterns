package com.aiengineeringpatterns.service.impl;


import com.aiengineeringpatterns.model.CustomerProfile;
import com.aiengineeringpatterns.model.Product;
import com.aiengineeringpatterns.service.ProductCatalog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InMemoryProductCatalog implements ProductCatalog {

    @Override
    public List<Product> getProducts() {
        return List.of(
                new Product(
                        "CC-001",
                        "Travel Rewards Card",
                        "TRAVEL",
                        99,
                        "Travel rewards, airport benefits and dining rewards"
                ),
                new Product(
                        "CC-002",
                        "Cashback Card",
                        "CASHBACK",
                        49,
                        "Simple cashback on everyday purchases"
                ),
                new Product(
                        "CC-003",
                        "Premium Travel Card",
                        "TRAVEL",
                        199,
                        "Premium travel rewards, lounge access and travel benefits"
                ),
                new Product(
                        "CC-004",
                        "Everyday Card",
                        "GENERAL",
                        0,
                        "No annual fee with basic rewards for everyday spending"
                )
        );
    }


    @Override
    public List<Product> getEligibleProducts(CustomerProfile customerProfile) {
        return getProducts()
                .stream()
                .filter(product ->
                        product.annualFee() <= customerProfile.maxAnnualFee())
                .toList();
    }

    @Override
    public List<Product> getRelevantProducts(CustomerProfile customerProfile) {

        return getEligibleProducts(customerProfile)
                .stream()
                .filter(product ->
                        product.category().equalsIgnoreCase(customerProfile.primarySpendingCategory()))
                .toList();
    }
}
