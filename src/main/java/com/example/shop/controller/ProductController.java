/**
 * The ProductController class is a REST controller that handles HTTP requests related to products.
 * It interacts with the ProductService to perform CRUD operations on the ProductEntity objects
 * and convert them to/from ProductDTO objects.
 */

package com.example.shop.controller;

import com.example.shop.dto.ProductDTO;
import com.example.shop.dto.ResponseDTO;
import com.example.shop.model.ProductEntity;
import com.example.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    // Test method to get ProductDTO response
    @GetMapping("/product-dto")
    public ResponseEntity<?> getProductDTO() {
        // Create a sample ProductEntity object
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId("1");
        productEntity.setTitle("Furniture Product");
        productEntity.setMaterial("Wood");
        productEntity.setPrice(1000.0);

        // Convert the ProductEntity to ProductDTO and send the response
        ProductDTO response = new ProductDTO(productEntity);
        return ResponseEntity.ok().body(response);
    }

    @Autowired
    private ProductService service;

    // Test method to get message response
    @GetMapping("/service-message")
    public ResponseEntity<?> getMessage() {
        String message = service.getMessage();

        List<String> list = new ArrayList<>();
        list.add(message);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();

        return ResponseEntity.ok().body(response);
    }

    // Retrieves the title of a ProductEntity object from the database through the ProductService,
    // wraps it in a ResponseDTO object and sends it as an HTTP response.
    @GetMapping("/service-title")
    public ResponseEntity<?> getTitle() {
        String title = service.getProductTitle();

        List<String> list = new ArrayList<>();
        list.add(title);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();

        return ResponseEntity.ok().body(response);
    }
}