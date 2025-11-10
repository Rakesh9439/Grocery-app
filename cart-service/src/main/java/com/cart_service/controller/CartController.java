package com.cart_service.controller;


import com.cart_service.dto.APIResponse;
import com.cart_service.dto.CartDto;
import com.cart_service.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {


    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    // ✅ 1️⃣ Create Cart or Add Item
    @PostMapping("/add")
    public ResponseEntity<APIResponse<String>> addItemToCart(
            @RequestBody CartDto cartDto) {

        APIResponse<String> response = cartService.addItemToCart(cartDto.getUserId(), cartDto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
