package com.ecommerce.ecomm.service;

import com.ecommerce.ecomm.model.Category;
import com.ecommerce.ecomm.payload.CategoryDTO;
import com.ecommerce.ecomm.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
