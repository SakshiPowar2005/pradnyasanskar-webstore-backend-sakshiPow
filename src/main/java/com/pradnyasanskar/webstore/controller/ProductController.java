package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.ProductRequestDTO;
import com.pradnyasanskar.webstore.dto.ProductResponseDTO;
import com.pradnyasanskar.webstore.entity.ProductStatus;
import com.pradnyasanskar.webstore.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create Product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO createProduct(
            @RequestBody ProductRequestDTO request) {

        return productService.createProduct(request);
    }

    // Get All Products
    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {

        return productService.getAllProducts();
    }

    // Get Product By ID
    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(
            @PathVariable Long id) {

        return productService.getProductById(id);
    }

    // Get Product By Slug
    @GetMapping("/slug/{slug}")
    public ProductResponseDTO getProductBySlug(
            @PathVariable String slug) {

        return productService.getProductBySlug(slug);
    }

    // Get Products By Category
    @GetMapping("/category/{categoryId}")
    public List<ProductResponseDTO> getProductsByCategory(
            @PathVariable Long categoryId) {

        return productService.getProductsByCategory(categoryId);
    }

    // Get Products By Status
    @GetMapping("/status/{status}")
    public List<ProductResponseDTO> getProductsByStatus(
            @PathVariable ProductStatus status) {

        return productService.getProductsByStatus(status);
    }

    // Update Product
    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO request) {

        return productService.updateProduct(id, request);
    }

    // Delete Product
    @DeleteMapping("/{id}")
    public String deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return "Product deleted successfully.";
    }

}