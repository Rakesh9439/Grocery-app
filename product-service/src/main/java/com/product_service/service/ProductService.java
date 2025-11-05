package com.product_service.service;

import com.product_service.dto.APIResponse;
import com.product_service.dto.ProductDto;

import java.util.List;

public interface ProductService {

    APIResponse<String> addProduct(ProductDto productDto);

    APIResponse<List<ProductDto>> getAllProducts();

    APIResponse<ProductDto> getProductById(Long id);
}
