package com.example.shop.service;

import com.example.shop.model.ProductEntity;
import com.example.shop.persistence.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// This ProductService class is a service layer component
// that interacts with the persistence layer through the injected ProductRepository.
@Service
public class ProductService {

    // Returns a test message
    public String getMessage() {
        return "This is a test message from the ProductService class.";
    }

    @Autowired
    private ProductRepository repository;

    // Creates a new ProductEntity object, saves it to the repository,
    // retrieves the saved entity by ID, and returns its title
    public String getProductTitle() {
        ProductEntity entity = ProductEntity.builder().title("Title of First Product").build();
        repository.save(entity);

        ProductEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }
}