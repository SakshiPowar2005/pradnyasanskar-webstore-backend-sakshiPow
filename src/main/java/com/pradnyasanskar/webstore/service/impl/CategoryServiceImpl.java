package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.CategoryRequestDTO;
import com.pradnyasanskar.webstore.dto.CategoryResponseDTO;
import com.pradnyasanskar.webstore.entity.Category;
import com.pradnyasanskar.webstore.repository.CategoryRepository;
import com.pradnyasanskar.webstore.repository.ProductRepository;
import com.pradnyasanskar.webstore.service.CategoryService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ProductRepository productRepository) {

        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    // ==========================
    // CREATE CATEGORY
    // ==========================
    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {

        if (categoryRepository.existsByCategoryName(dto.getCategoryName())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Category name already exists.");
        }

        if (categoryRepository.existsBySlug(dto.getSlug())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Slug already exists.");
        }

        Category category = new Category();

        category.setCategoryName(dto.getCategoryName());
        category.setSlug(dto.getSlug());
        category.setDescription(dto.getDescription());
        category.setImageUrl(dto.getImageUrl());
        category.setDisplayOrder(dto.getDisplayOrder());
        category.setIsActive(dto.getIsActive());

        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());

        if (dto.getParentCategoryId() != null) {

            Category parent = categoryRepository.findById(dto.getParentCategoryId())
                    .orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Parent category not found."));

            category.setParentCategory(parent);
        }

        Category savedCategory = categoryRepository.save(category);

        return mapToDTO(savedCategory);
    }

    // ==========================
    // GET ALL CATEGORIES
    // ==========================
    @Override
    public List<CategoryResponseDTO> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ==========================
    // GET ACTIVE CATEGORIES
    // ==========================
    @Override
    public List<CategoryResponseDTO> getActiveCategories() {

        return categoryRepository.findByIsActiveTrue()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ==========================
    // GET CATEGORY BY ID
    // ==========================
    @Override
    public CategoryResponseDTO getCategoryById(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category not found."));

        if (!category.getIsActive()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Category has been deleted.");
        }

        return mapToDTO(category);
    }

    // ==========================
    // GET CATEGORY BY SLUG
    // ==========================
    @Override
    public CategoryResponseDTO getCategoryBySlug(String slug) {

        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category not found."));

        if (!category.getIsActive()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Category has been deleted.");
        }

        return mapToDTO(category);
    }

    // ==========================
    // UPDATE CATEGORY
    // ==========================
    @Override
    public CategoryResponseDTO updateCategory(Long id,
                                              CategoryRequestDTO dto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category not found."));

        if (!category.getIsActive()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cannot update deleted category.");
        }

        category.setCategoryName(dto.getCategoryName());
        category.setSlug(dto.getSlug());
        category.setDescription(dto.getDescription());
        category.setImageUrl(dto.getImageUrl());
        category.setDisplayOrder(dto.getDisplayOrder());
        category.setIsActive(dto.getIsActive());

        category.setUpdatedAt(LocalDateTime.now());

        if (dto.getParentCategoryId() != null) {

            Category parent = categoryRepository.findById(dto.getParentCategoryId())
                    .orElseThrow(() ->
                            new ResponseStatusException(
                                    HttpStatus.NOT_FOUND,
                                    "Parent category not found."));

            category.setParentCategory(parent);

        } else {

            category.setParentCategory(null);
        }

        Category updatedCategory = categoryRepository.save(category);

        return mapToDTO(updatedCategory);
    }

    // ==========================
    // DELETE CATEGORY (SOFT DELETE)
    // ==========================
    @Override
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category not found."));

        if (!category.getIsActive()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Category already deleted.");
        }

        if (productRepository.existsByCategory(category)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Cannot delete category. Products are assigned to it.");
        }

        category.setIsActive(false);
        category.setUpdatedAt(LocalDateTime.now());

        categoryRepository.save(category);
    }

    // ==========================
    // ENTITY -> DTO
    // ==========================
    private CategoryResponseDTO mapToDTO(Category category) {

        CategoryResponseDTO dto = new CategoryResponseDTO();

        dto.setCategoryId(category.getCategoryId());

        if (category.getParentCategory() != null) {
            dto.setParentCategoryId(
                    category.getParentCategory().getCategoryId());
        }

        dto.setCategoryName(category.getCategoryName());
        dto.setSlug(category.getSlug());
        dto.setDescription(category.getDescription());
        dto.setImageUrl(category.getImageUrl());
        dto.setDisplayOrder(category.getDisplayOrder());
        dto.setIsActive(category.getIsActive());

        return dto;
    }
}