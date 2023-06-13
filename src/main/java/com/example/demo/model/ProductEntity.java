package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "product")
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

    // Map product to a user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private String title;
    private String material;
    private Double price;
    private String company;
}
