package com.example.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
// Lombok annotations to generate boilerplate code
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductEntity {

    // Primary key for the ProductEntity table
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    // ID of the user who posted the product
    private String userId;

    // Title or name of the product
    private String title;

    // Material used to make the product
    private String material;

    // Price of the product
    private Double price;
}
