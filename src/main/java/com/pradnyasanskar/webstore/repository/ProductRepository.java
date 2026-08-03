package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.Product;
import com.pradnyasanskar.webstore.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import com.pradnyasanskar.webstore.entity.Category;
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySlug(String slug);

    boolean existsBySlug(String slug);

    List<Product> findByCategoryCategoryId(Long categoryId);

    List<Product> findByProductStatus(ProductStatus productStatus);

    boolean existsByCategory(Category category);

    List<Product> findByIsActiveTrue();

    Optional<Product> findByProductIdAndIsActiveTrue(Long productId);

    Optional<Product> findBySlugAndIsActiveTrue(String slug);

    List<Product> findByCategoryCategoryIdAndIsActiveTrue(Long categoryId);

    List<Product> findByProductStatusAndIsActiveTrue(ProductStatus status);
}