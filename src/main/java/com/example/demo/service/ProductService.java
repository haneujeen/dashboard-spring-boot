package com.example.demo.service;

import com.example.demo.model.ProductEntity;
import com.example.demo.persistence.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This service class interacts with the product controller and product repository. This includes:
 *
 * getService(), getTitle(): Test a service class
 * create(final ProductEntity entity): Creates a new product
 * update(final ProductEntity entity): Updates an existing product
 * delete(String productId): Deletes a product
 * validate(final ProductEntity entity): Validates a product
 * retrieve(String productId): Retrieves a product
 */
@Slf4j
@Service
public class ProductService {

    public String getMessage() {
        return "ProductService test";
    }

    // Create a new product entity only with its title, find it by ID, and return the title
    public String getProductTitle() {
        ProductEntity entity = ProductEntity.builder().title("Sample Product").build();
        repository.save(entity);

        ProductEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    @Autowired
    private ProductRepository repository;

    /**
     * Create and save a new product entity.
     *
     * @param entity The product entity to create and save.
     * @return A list of all products with the same user ID as the created entity.
     */
    public List<ProductEntity> create(final ProductEntity entity) {
        validate(entity);
        repository.save(entity);
        log.info("Product ID {} created", entity.getId());

        return repository.findByUserId(entity.getUser().getId());
    }

    /**
     * Updates the title of an existing product entity in the database.
     * Returns a list of all products with the same user ID as the updated entity.
     *
     * @param entity The product entity to update.
     * @return A list of all products with the same user ID as the updated entity.
     */
    public List<ProductEntity> update(final ProductEntity entity) {
        validate(entity);

        ProductEntity currentEntity = repository.findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        currentEntity.setTitle(entity.getTitle());
        currentEntity.setUser(entity.getUser());
        if (entity.getMaterial() != null) {
            currentEntity.setMaterial(entity.getMaterial());
        }
        if (entity.getPrice() != null) {
            currentEntity.setPrice(entity.getPrice());
        }
        if (entity.getCompany() != null) {
            currentEntity.setCompany(entity.getCompany());
        }
        repository.save(currentEntity);

        return retrieve(entity.getUser().getId());
    }

    /**
     * Deletes product from the database.
     *
     * @param entity The product entity to delete.
     * @return A list of all products with the same user ID as the deleted entity.
     * @throws RuntimeException if an error occurs.
     */
    public List<ProductEntity> delete(final ProductEntity entity) {
        validate(entity);

        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error("Failed deleting product ID " + entity.getId() + ": " + e.getMessage());
            throw new RuntimeException("Error deleting product ID " + entity.getId() + ": " + e.getMessage());
        }

        return retrieve(entity.getUser().getId());
    }

    /**
     * Validates a product entity by itself and user.
     *
     * @param entity The product entity to validate.
     * @throws RuntimeException if the entity is null or if the user ID is null.
     */
    private static void validate(final ProductEntity entity) {
        if (entity == null) {
            log.warn("Product entity is null");
            throw new RuntimeException("Product entity cannot be null");
        }
        if (entity.getUser() == null) {
            log.warn("User is null");
            throw new RuntimeException("User cannot be null");
        }
    }

    public List<ProductEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }
}