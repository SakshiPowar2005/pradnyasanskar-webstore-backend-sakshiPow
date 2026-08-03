package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find category by slug
    Optional<Category> findBySlug(String slug);

    // Check if category name already exists
    boolean existsByCategoryName(String categoryName);

    // Check if slug already exists
    boolean existsBySlug(String slug);

    // Get all active categories
    List<Category> findByIsActiveTrue();

}