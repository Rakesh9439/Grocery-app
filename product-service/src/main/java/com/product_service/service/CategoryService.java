package com.product_service.service;

import com.product_service.dto.APIResponse;
import com.product_service.dto.CategoryDto;

public interface CategoryService {

    APIResponse<String> addCategory(CategoryDto categoryDto);
}
