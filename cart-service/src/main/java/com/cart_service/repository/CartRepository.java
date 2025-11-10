package com.cart_service.repository;

import com.cart_service.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // 🔹 Find cart by userId
    Optional<Cart> findByUserId(Long userId);

    // 🔹 Check if user already has a cart
    boolean existsByUserId(Long userId);

    // 🔹 Delete cart by userId (after checkout, optional)
    void deleteByUserId(Long userId);
}
