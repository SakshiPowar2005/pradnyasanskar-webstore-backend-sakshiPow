package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository
        extends JpaRepository<ProductVariant, Long> {

    Optional<ProductVariant> findBySku(String sku);

    boolean existsBySku(String sku);

    List<ProductVariant> findByProductProductIdAndIsActiveTrue(Long productId);

    List<ProductVariant> findByIsActiveTrue();

    Optional<ProductVariant> findByVariantIdAndIsActiveTrue(Long variantId);
}