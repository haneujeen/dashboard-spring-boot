package com.example.shop.dto;

import com.example.shop.model.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for representing a product.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {

    private String id;
    private String title;
    private String material;
    private Double price;
    private String company;
    private String username;

    /**
     * Constructor for creating a ProductDTO from a ProductEntity.
     *
     * @param entity the ProductEntity to be converted
     */
    public ProductDTO(final ProductEntity entity) {
        this.title = entity.getTitle();
        this.material = entity.getMaterial();
        this.price = entity.getPrice();
        this.company = entity.getCompany();
        this.username = entity.getUser().getUsername();
    }

    /**
     * Convert a ProductDTO object back into ProductEntity object (for creating and updating purposes).
     *
     * @param dto the ProductDTO to be converted
     * @return a ProductEntity
     */
    public static ProductEntity toEntity(final ProductDTO dto) {

        return ProductEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .material(dto.getMaterial())
                .price(dto.getPrice())
                .company(dto.getCompany())
                .build();
    }
}