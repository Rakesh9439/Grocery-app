package com.product_service.repository;

import com.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // ✅ Find products by category id
   // List<Product> findByCategoryId(Long categoryId);

    // ✅ Check if product exists by name
    boolean existsByNameIgnoreCase(String name);

}