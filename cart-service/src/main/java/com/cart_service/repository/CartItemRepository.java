package com.cart_service.repository;

import com.cart_service.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // 🔹 Find all items by cartId
    List<CartItem> findByCartId(Long cartId);

    // 🔹 Find a specific item in a cart by productId
    CartItem findByCartIdAndProductId(Long cartId, Long productId);

    // 🔹 Delete all items when cart is cleared
    void deleteByCartId(Long cartId);
}