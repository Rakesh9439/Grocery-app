package com.cart_service.service;

import com.cart_service.dto.APIResponse;
import com.cart_service.dto.CartDto;

public interface CartService {

        APIResponse<String> addItemToCart(Long userId, CartDto cartDto);
}
