package com.cart_service.service;

import com.cart_service.dto.APIResponse;
import com.cart_service.dto.CartItemDto;

public interface CartItemService {

    // ➕ Add a product to cart
    APIResponse<String> addItemToCart(Long cartId, CartItemDto cartItemDto);
}
