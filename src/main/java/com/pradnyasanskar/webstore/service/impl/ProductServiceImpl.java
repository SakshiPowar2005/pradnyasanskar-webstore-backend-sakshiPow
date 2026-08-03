package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.ProductRequestDTO;
import com.pradnyasanskar.webstore.dto.ProductResponseDTO;
import com.pradnyasanskar.webstore.entity.Category;
import com.pradnyasanskar.webstore.entity.Product;
import com.pradnyasanskar.webstore.entity.ProductStatus;
import com.pradnyasanskar.webstore.repository.CategoryRepository;
import com.pradnyasanskar.webstore.repository.ProductRepository;
import com.pradnyasanskar.webstore.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO request) {

        if (productRepository.existsBySlug(request.getSlug())) {
            throw new RuntimeException("Product slug already exists.");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found."));

        Product product = new Product();

        product.setCategory(category);
        product.setProductName(request.getProductName());
        product.setSlug(request.getSlug());
        product.setBrand(request.getBrand());
        product.setManufacturer(request.getManufacturer());
        product.setDescription(request.getDescription());
        product.setComposition(request.getComposition());
        product.setDosageForm(request.getDosageForm());
        product.setPrescriptionRequired(request.getPrescriptionRequired());
        product.setProductStatus(request.getProductStatus());
        product.setIsActive(true);

        // Set timestamps
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {

        return productRepository.findByIsActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getProductById(Long productId) {

        Product product = productRepository
                .findByProductIdAndIsActiveTrue(productId)
                .orElseThrow(() -> new RuntimeException("Product not found."));

        return mapToResponse(product);
    }

    @Override
    public ProductResponseDTO getProductBySlug(String slug) {

        Product product = productRepository.findBySlugAndIsActiveTrue(slug)
                .orElseThrow(() -> new RuntimeException("Product not found."));

        return mapToResponse(product);
    }

    @Override
    public List<ProductResponseDTO> getProductsByCategory(Long categoryId) {

        return productRepository.findByCategoryCategoryIdAndIsActiveTrue(categoryId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> getProductsByStatus(ProductStatus status) {

        return productRepository.findByProductStatusAndIsActiveTrue(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId,
                                            ProductRequestDTO request) {

        Product product = productRepository.findByProductIdAndIsActiveTrue(productId)
                .orElseThrow(() -> new RuntimeException("Product not found."));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found."));

        // Prevent duplicate slug
        if (!product.getSlug().equals(request.getSlug())
                && productRepository.existsBySlug(request.getSlug())) {

            throw new RuntimeException("Product slug already exists.");
        }

        product.setCategory(category);
        product.setProductName(request.getProductName());
        product.setSlug(request.getSlug());
        product.setBrand(request.getBrand());
        product.setManufacturer(request.getManufacturer());
        product.setDescription(request.getDescription());
        product.setComposition(request.getComposition());
        product.setDosageForm(request.getDosageForm());
        product.setPrescriptionRequired(request.getPrescriptionRequired());
        product.setProductStatus(request.getProductStatus());

        // Update timestamp
        product.setUpdatedAt(LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new RuntimeException("Product not found."));

        if (!product.getIsActive()) {
            throw new RuntimeException("Product already deleted.");
        }

        product.setIsActive(false);
        product.setUpdatedAt(LocalDateTime.now());

        productRepository.save(product);
    }

    private ProductResponseDTO mapToResponse(Product product) {

        ProductResponseDTO response = new ProductResponseDTO();

        response.setProductId(product.getProductId());
        response.setCategoryId(product.getCategory().getCategoryId());
        response.setCategoryName(product.getCategory().getCategoryName());
        response.setProductName(product.getProductName());
        response.setSlug(product.getSlug());
        response.setBrand(product.getBrand());
        response.setManufacturer(product.getManufacturer());
        response.setDescription(product.getDescription());
        response.setComposition(product.getComposition());
        response.setDosageForm(product.getDosageForm());
        response.setPrescriptionRequired(product.getPrescriptionRequired());
        response.setProductStatus(product.getProductStatus());

        return response;
    }
}