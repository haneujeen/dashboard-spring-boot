package com.example.shop.dto;

import com.example.shop.model.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for representing a product.
 * This class does not include the userId field, as it will be represented in a separate DTO class for user information.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {

    private String id;      // Primary key for the ProductEntity table
    private String title;   // Title or name of the product
    private String material;// Material used to make the product
    private Double price;   // Price of the product

    /**
     * Constructor for creating a ProductDTO from a ProductEntity.
     *
     * @param entity the ProductEntity to be converted into a ProductDTO
     */
    public ProductDTO(final ProductEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.material = entity.getMaterial();
        this.price = entity.getPrice();
    }
}