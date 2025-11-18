package com.cart_service.service.impl;

import com.cart_service.dto.APIResponse;
import com.cart_service.dto.CartItemDto;
import com.cart_service.entity.Cart;
import com.cart_service.entity.CartItem;
import com.cart_service.repository.CartItemRepository;
import com.cart_service.repository.CartRepository;
import com.cart_service.service.CartItemService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartItemServiceImpl implements CartItemService {

    private CartItemRepository cartItemRepository;

    private CartRepository cartRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public APIResponse<String> addItemToCart(Long cartId, CartItemDto cartItemDto) {


        APIResponse<String> response = new APIResponse<>();

        // Check if cart exists
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
           // response.setSuccess(false);
            response.setStatus(404);
            response.setMessage("Cart not found with ID: " + cartId);
            response.setData(null);
          //  response.setTimestamp(LocalDateTime.now());
            return response;
        }


        // Check if product already exists in this cart
        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cartId, cartItemDto.getProductId());
        if (existingItem != null) {
             //response.setSuccess(false);
            response.setStatus(400);
            response.setMessage("Product already exists in cart ID: " + cartId);
            response.setData(null);
         //   response.setTimestamp(LocalDateTime.now());
            return response;
        }

        // Create new CartItem
        CartItem newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setProductId(cartItemDto.getProductId());
        newItem.setProductName(cartItemDto.getProductName());
        newItem.setPrice(cartItemDto.getPrice());
        newItem.setQuantity(cartItemDto.getQuantity());
        newItem.setSubtotal(cartItemDto.getPrice().multiply(BigDecimal.valueOf(cartItemDto.getQuantity())));

        cartItemRepository.save(newItem);

        //response.setSuccess(true);
        response.setStatus(201);
        response.setMessage("Product added successfully to cart ID: " + cartId);
        response.setData("CartItem ID: " + newItem.getId());
       //   response.setTimestamp(LocalDateTime.now());
        return response;
      //  return null;
    }
}
