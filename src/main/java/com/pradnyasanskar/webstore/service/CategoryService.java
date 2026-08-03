package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.CategoryRequestDTO;
import com.pradnyasanskar.webstore.dto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    // Create a new category
    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO);

    // Get all categories
    List<CategoryResponseDTO> getAllCategories();

    // Get only active categories
    List<CategoryResponseDTO> getActiveCategories();

    // Get category by ID
    CategoryResponseDTO getCategoryById(Long categoryId);

    // Get category by slug
    CategoryResponseDTO getCategoryBySlug(String slug);

    // Update category
    CategoryResponseDTO updateCategory(Long categoryId,
                                       CategoryRequestDTO categoryRequestDTO);

    // Delete category
    void deleteCategory(Long categoryId);
}