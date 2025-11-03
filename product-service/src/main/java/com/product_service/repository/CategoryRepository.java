package com.product_service.repository;

import com.product_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // ✅ Check if category exists by name (case-insensitive)
    boolean existsByNameIgnoreCase(String name);
}