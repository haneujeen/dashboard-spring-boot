package com.example.shop.controller;

import com.example.shop.dto.ProductDTO;
import com.example.shop.dto.ResponseDTO;
import com.example.shop.model.ProductEntity;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for handling product-related HTTP requests.
 * Interacts with the ProductService to perform CRUD operations on the ProductEntity objects
 * and convert them to/from ProductDTO objects.
 *
 * DTO objects are wrapped into ResponseDTO objects and sent to the client as HTTP responses.
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    // Create a sample product
    @GetMapping("/test")
    public ResponseEntity<?> getProduct() {
        ProductEntity entity = new ProductEntity();
        entity.setTitle("Silicone Spatula");
        entity.setMaterial("Silicone");
        entity.setCompany("AmazonBasics");
        entity.setPrice(6.99);

        // Convert the ProductEntity to ProductDTO to send the response
        ProductDTO response = new ProductDTO(entity);
        return ResponseEntity.ok().body(response);
    }

    // Test getMessage()
    @GetMapping("/test/message")
    public ResponseEntity<?> getMessage() {
        String message = productService.getMessage();

        List<String> list = new ArrayList<>();
        list.add(message);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();

        return ResponseEntity.ok().body(response);
    }

    // Test getProductTitle()
    @GetMapping("/test/title")
    public ResponseEntity<?> getTitle() {
        String title = productService.getProductTitle();
        List<String> list = new ArrayList<>();
        list.add(title);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();

        return ResponseEntity.ok().body(response);
    }

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    /**
     * Endpoint for creating a new product.
     *
     * @param userId the user ID
     * @param dto the ProductDTO object to be created
     * @return ResponseEntity containing a list of created products wrapped in a ResponseDTO object,
     *         or an error message if an exception occurs.
     */
    @PostMapping
    public ResponseEntity<?> createProduct(@AuthenticationPrincipal String userId, @RequestBody ProductDTO dto) {

        try {
            // Convert the received ProductDTO object to a ProductEntity object
            ProductEntity entity = ProductDTO.toEntity(dto);

            // Set the ID to null to ensure the ProductEntity object gets a new ID
            entity.setId(null);

            // Set the user of the ProductEntity object to the authenticated user
            entity.setUser(userService.findById(userId));

            // Call create method from service layer and create new product entity in the database
            List<ProductEntity> entities = productService.create(entity);

            // Convert the entity objects to DTO objects
            List<ProductDTO> dtos = entities.stream()
                    .map(ProductDTO::new)
                    .collect(Collectors.toList());

            // Create a ResponseDTO object to wrap the ProductDTO objects and send it as an HTTP response
            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder()
                    .data(dtos)
                    .build();

            // Return an HTTP response with a status code of 200 (OK) and the ResponseDTO object as the body
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // If an exception is caught, create a ResponseDTO object with an error message and send it as a bad request
            String errorMessage = e.getMessage();
            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder()
                    .errorMessage(errorMessage)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Retrieves a ProductDTO objects.
     *
     * @param userId the user ID
     * @return ResponseEntity with a list of retrieved products wrapped in a ResponseDTO object
     */
    @GetMapping
    public ResponseEntity<?> retrieveProducts(@AuthenticationPrincipal String userId) {
        // Retrieve all ProductEntity objects of the authenticated user
        List<ProductEntity> entities = productService.retrieve(userId);

        List<ProductDTO> dtos = entities.stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());

        ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder()
                .data(dtos)
                .build();
        return ResponseEntity.ok().body(response);
    }

    /**
     * Endpoint for updating a product.
     *
     * @param userId the user ID
     * @param dto the ProductDTO object
     * @return ResponseEntity containing a list of updated products wrapped in a ResponseDTO object,
     *         or an error message if an exception occurs.
     */
    @PutMapping
    public ResponseEntity<?> updateProduct(@AuthenticationPrincipal String userId, @RequestBody ProductDTO dto) {
        try {
            ProductEntity entity = ProductDTO.toEntity(dto);
            entity.setUser(userService.findById(userId));

            List<ProductEntity> entities = productService.update(entity);

            List<ProductDTO> dtos = entities.stream()
                    .map(ProductDTO::new)
                    .collect(Collectors.toList());

            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder()
                    .data(dtos)
                    .build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder()
                    .errorMessage(errorMessage)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Endpoint for deleting a product by ID.
     *
     * @param userId the user ID
     * @param dto the ProductDTO object
     * @return ResponseEntity containing a list of remaining products wrapped in a ResponseDTO object,
     *         or an error message if an exception occurs.
     */
    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@AuthenticationPrincipal String userId, @RequestBody ProductDTO dto) {
        try {
            ProductEntity entity = ProductDTO.toEntity(dto);

            entity.setUser(userService.findById(userId));

            List<ProductEntity> entities = productService.delete(entity);

            List<ProductDTO> dtos = entities.stream()
                    .map(ProductDTO::new)
                    .collect(Collectors.toList());

            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder()
                    .data(dtos)
                    .build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder()
                    .errorMessage(errorMessage)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}