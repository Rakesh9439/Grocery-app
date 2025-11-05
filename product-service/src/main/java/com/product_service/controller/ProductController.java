package com.product_service.controller;


import com.product_service.dto.APIResponse;
import com.product_service.dto.ProductDto;
import com.product_service.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    // ✅ 1. Add Product
    @PostMapping("/addProduct")
    public ResponseEntity<APIResponse<String>> addProduct(@RequestBody ProductDto productDto) {
        APIResponse<String> response = productService.addProduct(productDto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    // 🧾 2️⃣ Get All Products
    @GetMapping
    public ResponseEntity<APIResponse<List<ProductDto>>> getAllProducts() {
        APIResponse<List<ProductDto>> response = productService.getAllProducts();
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    // ✅ 3️⃣ Get Product By ID
    @GetMapping("/{id}")
    public APIResponse<ProductDto> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
}
