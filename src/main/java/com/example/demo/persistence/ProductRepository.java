package com.example.demo.persistence;

import com.example.demo.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    List<ProductEntity> findByUserId(String userId);

    // Custom query to retrieve a list of products for a given user ID:
    // @Query(value = "select * from Product p where p.userId = ?1",
    //          nativeQuery = true)
}