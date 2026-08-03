package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.ProductRequestDTO;
import com.pradnyasanskar.webstore.dto.ProductResponseDTO;
import com.pradnyasanskar.webstore.entity.ProductStatus;

import java.util.List;

public interface ProductService {

    // Create a new product
    ProductResponseDTO createProduct(ProductRequestDTO request);

    // Get all products
    List<ProductResponseDTO> getAllProducts();

    // Get product by ID
    ProductResponseDTO getProductById(Long productId);

    // Get product by slug
    ProductResponseDTO getProductBySlug(String slug);

    // Get all products of a category
    List<ProductResponseDTO> getProductsByCategory(Long categoryId);

    // Get products by status (ACTIVE, INACTIVE, DISCONTINUED)
    List<ProductResponseDTO> getProductsByStatus(ProductStatus status);

    // Update product
    ProductResponseDTO updateProduct(Long productId, ProductRequestDTO request);

    // Delete product
    void deleteProduct(Long productId);

}