package com.example.shop.persistence;

import com.example.shop.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    List<ProductEntity> findByUserId(String userId);

    // Custom query to retrieve a list of products for a given user ID:
    // @Query(value = "select * from Product p where p.userId = ?1",
    //          nativeQuery = true)
}