package com.cart_service.service.impl;

import com.cart_service.dto.APIResponse;
import com.cart_service.dto.CartDto;
import com.cart_service.entity.Cart;
import com.cart_service.repository.CartRepository;
import com.cart_service.service.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public APIResponse<String> addItemToCart(Long userId, CartDto cartDto) {

            APIResponse<String> response = new APIResponse<>();


        // ✅ 1️⃣ Validate user
        if (cartDto.getUserId() == null || cartDto.getUserId() <= 0) {
            //response.setSuccess(false);
            response.setStatus(400);
            response.setMessage("Invalid user ID.");
            response.setData(null);
          //  response.setTimestamp(java.time.LocalDateTime.now());
            return response;
        }


        // Check cart already exists
        if (cartRepository.existsByUserId(cartDto.getUserId())) {
            response.setMessage("cart  already exists with name: " + cartDto.getUserId());
            response.setStatus(400);
            response.setData(null);
            return response;
        }


        // ✅ 3️⃣ Create new cart
        Cart cart = new Cart();
        cart.setUserId(cartDto.getUserId());
        cartRepository.save(cart);

        // ✅ 4️⃣ Build success response
       // response.setSuccess(true);
        response.setStatus(201);
        response.setMessage("Cart created successfully for user ID: " + cartDto.getUserId());
        response.setData("Cart ID: " + cart.getId());
       // response.setTimestamp(java.time.LocalDateTime.now());

        return response;
    }
}
