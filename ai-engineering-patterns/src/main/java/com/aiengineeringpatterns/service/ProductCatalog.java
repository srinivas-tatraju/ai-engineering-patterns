package com.aiengineeringpatterns.service;

import com.aiengineeringpatterns.model.CustomerProfile;
import com.aiengineeringpatterns.model.Product;
import java.util.List;

public interface ProductCatalog {
    List<Product> getProducts();

    List<Product> getEligibleProducts(CustomerProfile customerProfile);

    List<Product> getRelevantProducts(CustomerProfile customerProfile);
}
