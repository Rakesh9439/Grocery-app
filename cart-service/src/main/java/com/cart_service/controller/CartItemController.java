package com.cart_service.controller;


import com.cart_service.dto.APIResponse;
import com.cart_service.dto.CartItemDto;
import com.cart_service.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/cart-item")
public class CartItemController {


    private CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }


    // ✅ 1️⃣ Add Item to Cart
    @PostMapping("/add/{cartId}")
    public ResponseEntity<APIResponse<String>> addItemToCart(
            @PathVariable Long cartId,
            @RequestBody CartItemDto cartItemDto) {

        APIResponse<String> response = cartItemService.addItemToCart(cartId, cartItemDto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
