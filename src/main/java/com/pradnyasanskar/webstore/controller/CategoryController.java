package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.CategoryRequestDTO;
import com.pradnyasanskar.webstore.dto.CategoryResponseDTO;
import com.pradnyasanskar.webstore.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // ==========================
    // CREATE CATEGORY
    // POST /api/categories
    // ==========================
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @RequestBody CategoryRequestDTO dto) {

        CategoryResponseDTO response =
                categoryService.createCategory(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ==========================
    // GET ALL CATEGORIES
    // GET /api/categories
    // ==========================
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {

        List<CategoryResponseDTO> categories =
                categoryService.getAllCategories();

        return ResponseEntity.ok(categories);
    }

    // ==========================
    // GET ACTIVE CATEGORIES
    // GET /api/categories/active
    // ==========================
    @GetMapping("/active")
    public ResponseEntity<List<CategoryResponseDTO>> getActiveCategories() {

        List<CategoryResponseDTO> categories =
                categoryService.getActiveCategories();

        return ResponseEntity.ok(categories);
    }

    // ==========================
    // GET CATEGORY BY ID
    // GET /api/categories/{id}
    // ==========================
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(
            @PathVariable Long id) {

        CategoryResponseDTO category =
                categoryService.getCategoryById(id);

        return ResponseEntity.ok(category);
    }

    // ==========================
    // GET CATEGORY BY SLUG
    // GET /api/categories/slug/{slug}
    // ==========================
    @GetMapping("/slug/{slug}")
    public ResponseEntity<CategoryResponseDTO> getCategoryBySlug(
            @PathVariable String slug) {

        CategoryResponseDTO category =
                categoryService.getCategoryBySlug(slug);

        return ResponseEntity.ok(category);
    }

    // ==========================
    // UPDATE CATEGORY
    // PUT /api/categories/{id}
    // ==========================
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequestDTO dto) {

        CategoryResponseDTO updated =
                categoryService.updateCategory(id, dto);

        return ResponseEntity.ok(updated);
    }

    // ==========================
    // DELETE CATEGORY
    // DELETE /api/categories/{id}
    // ==========================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.ok("Category deleted successfully.");
    }
}