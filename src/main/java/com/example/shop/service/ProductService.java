package com.example.shop.service;

import com.example.shop.model.ProductEntity;
import com.example.shop.persistence.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// This ProductService class is a service layer component
// that interacts with the persistence layer through the injected ProductRepository.
@Slf4j
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

    /**
     * Creates a new product entity and saves it to the database.
     * Returns a list of all products with the same user ID as the created entity.
     *
     * @param entity The product entity to create and save.
     * @return A list of all products with the same user ID as the created entity.
     * @throws RuntimeException if the entity is null or if the user ID is null.
     */
    public List<ProductEntity> create(final ProductEntity entity) {

        // Validate the entity before saving it to the database
        validate(entity);

        // Save the entity to the database
        repository.save(entity);

        // Log the ID of the created product entity
        log.info("Product with ID {} created", entity.getId());

        // Return a list of all products with the same user ID as the created entity
        return repository.findByUserId(entity.getUserId());
    }

    /**
     * Validates a product entity.
     *
     * @param entity The product entity to validate.
     * @throws RuntimeException if the entity is null or if the user ID is null.
     */
    private static void validate(ProductEntity entity) {
        // Throw a runtime exception if the entity is null
        if (entity == null) {
            log.warn("The product entity is null");
            throw new RuntimeException("Product entity cannot be null");
        }

        // Throw a runtime exception if the user ID is null
        if (entity.getUserId() == null) {
            log.warn("The user ID is null");
            throw new RuntimeException("User ID cannot be null");
        }
    }
}