package com.product_service.service.impl;

import com.product_service.dto.APIResponse;
import com.product_service.dto.CategoryDto;
import com.product_service.entity.Category;
import com.product_service.repository.CategoryRepository;
import com.product_service.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public APIResponse<String> addCategory(CategoryDto categoryDto) {

        APIResponse<String> response = new APIResponse<>();
        // ✅ Check if category already exists
        if (categoryRepository.existsByNameIgnoreCase(categoryDto.getName())) {
            response.setMessage("Category already exists with name: " + categoryDto.getName());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setData(null);
            return response;
        }

        // ✅ Save new Category
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        categoryRepository.save(category);

        // ✅ Return success response
        response.setMessage("Category added successfully");
        response.setStatus(HttpStatus.CREATED.value());
        response.setData("Category ID: " + category.getId());

        return response;




    }
}
