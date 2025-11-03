package com.product_service.controller;


import com.product_service.dto.APIResponse;
import com.product_service.dto.CategoryDto;
import com.product_service.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ✅ POST API — Add New Category
    @PostMapping
    public ResponseEntity<APIResponse<String>> addCategory(@RequestBody CategoryDto categoryDto) {
        APIResponse<String> response = categoryService.addCategory(categoryDto);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
