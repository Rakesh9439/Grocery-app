package com.product_service.service.impl;

import com.product_service.dto.APIResponse;
import com.product_service.dto.ProductDto;
import com.product_service.entity.Category;
import com.product_service.entity.Product;
import com.product_service.repository.CategoryRepository;
import com.product_service.repository.ProductRepository;
import com.product_service.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public APIResponse<String> addProduct(ProductDto productDto) {

        APIResponse<String> response = new APIResponse<>();

        // Check product already exists
        if (productRepository.existsByNameIgnoreCase(productDto.getName())) {
            response.setMessage("Product already exists with name: " + productDto.getName());
            response.setStatus(400);
            response.setData(null);
            return response;
        }

        // Check category exists
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElse(null);

        if (category == null) {
            response.setMessage("Category not found with ID: " + productDto.getCategoryId());
            response.setStatus(404);
            response.setData(null);
            return response;
        }

        // Save product
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setImageUrl(productDto.getImageUrl());
        product.setStatus(product.getStock() > 0 ? "AVAILABLE" : "OUT_OF_STOCK");
        product.setCategory(category);
        productRepository.save(product);

        response.setMessage("Product added successfully!");
        response.setStatus(201);
        response.setData("Product ID: " + product.getId());
        return response;
    }


    // 🧾 Fetch All Products
    @Override
    public APIResponse<List<ProductDto>> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductDto> productDtos = products.stream().map(product -> {
            ProductDto dto = new ProductDto();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setPrice(product.getPrice());
            dto.setStock(product.getStock());
            dto.setImageUrl(product.getImageUrl());
            dto.setCategoryId(product.getCategory().getId());
            dto.setStatus(product.getStock() > 0 ? "AVAILABLE" : "OUT_OF_STOCK");
            dto.setCategoryName(product.getCategory().getName()); // ✅ category added properly
            return dto;
        }).collect(Collectors.toList());

        APIResponse<List<ProductDto>> response = new APIResponse<>();
        response.setStatus(200);
        response.setMessage("Product list fetched successfully");
        response.setData(productDtos);

        return response;
    }

    @Override
    public APIResponse<ProductDto> getProductById(Long id) {


        APIResponse<ProductDto> response = new APIResponse<>();

        // 1️⃣ Product  DB
        Product product = productRepository.findById(id).orElse(null);

        // 2️⃣ अगर Product
        if (product == null) {
            response.setStatus(404);
            response.setMessage("Product not found with ID: " + id);
            response.setData(null);
            return response;
        }

        // 3️⃣ Entity → DTO  Convert
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setImageUrl(product.getImageUrl());
        dto.setStatus(product.getStock() > 0 ? "AVAILABLE" : "OUT_OF_STOCK");

        // Category safe  (Lazy loading safe)
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        } else {
            dto.setCategoryName(null);
            dto.setCategoryId(null);
        }

        // 4️⃣ Success Response
        response.setStatus(200);
        response.setMessage("Product fetched successfully");
        response.setData(dto);

        return response;


    }


}
