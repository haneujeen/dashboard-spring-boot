package com.example.shop.service;

import com.example.shop.model.ProductEntity;
import com.example.shop.persistence.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
     * Updates the title of an existing product entity in the database.
     * Returns a list of all products with the same user ID as the updated entity.
     *
     * @param entity The product entity to update.
     * @return A list of all products with the same user ID as the updated entity.
     * @throws RuntimeException if the entity is null or if the ID or user ID is null.
     */
    public List<ProductEntity> update(final ProductEntity entity) {

        // Validate the entity before updating it in the database
        validate(entity);

        // Retrieve the existing entity from the database
        Optional<ProductEntity> optionalEntity = repository.findById(entity.getId());

        // Update the title of the entity if it exists in the database
        optionalEntity.ifPresent(product -> {
            product.setTitle(entity.getTitle());
            repository.save(product);
        });

        // Return a list of all products with the same user ID as the updated entity
        return retrieve(entity.getUserId());
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

    /**
     * Deletes an existing product entity from the database.
     * Returns a list of all products with the same user ID as the deleted entity.
     *
     * @param entity The product entity to delete.
     * @return A list of all products with the same user ID as the deleted entity.
     * @throws RuntimeException if the entity is null or if the ID or user ID is null,
     *         or if an error occurs while deleting the entity.
     */
    public List<ProductEntity> delete(final ProductEntity entity) {

        // Validate the entity before deleting it from the database
        validate(entity);

        try {
            // Attempt to delete the entity from the database
            repository.delete(entity);
        } catch (Exception e) {
            // Log the error message with the ID of the entity
            log.error("Error deleting product with ID " + entity.getId() + ": " + e.getMessage());
            // Throw a runtime exception with the error message
            throw new RuntimeException("Error deleting product with ID " + entity.getId() + ": " + e.getMessage());
        }

        // Return a list of all products with the same user ID as the deleted entity
        return retrieve(entity.getUserId());
    }

    // Retrieves a list of all products with the given user ID from the repository
    public List<ProductEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }
}