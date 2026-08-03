package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.ProductVariantRequestDTO;
import com.pradnyasanskar.webstore.dto.ProductVariantResponseDTO;
import com.pradnyasanskar.webstore.service.ProductVariantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-variants")
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    public ProductVariantController(ProductVariantService productVariantService) {
        this.productVariantService = productVariantService;
    }

    // Create Variant
    @PostMapping
    public ProductVariantResponseDTO createVariant(
            @RequestBody ProductVariantRequestDTO request) {

        return productVariantService.createVariant(request);
    }

    // Get All Variants
    @GetMapping
    public List<ProductVariantResponseDTO> getAllVariants() {

        return productVariantService.getAllVariants();
    }

    // Get Variant By Id
    @GetMapping("/{id}")
    public ProductVariantResponseDTO getVariantById(
            @PathVariable Long id) {

        return productVariantService.getVariantById(id);
    }

    // Get Variants By Product
    @GetMapping("/product/{productId}")
    public List<ProductVariantResponseDTO> getVariantsByProduct(
            @PathVariable Long productId) {

        return productVariantService.getVariantsByProduct(productId);
    }

    // Get Active Variants
    @GetMapping("/active")
    public List<ProductVariantResponseDTO> getActiveVariants() {

        return productVariantService.getActiveVariants();
    }

    // Update Variant
    @PutMapping("/{id}")
    public ProductVariantResponseDTO updateVariant(
            @PathVariable Long id,
            @RequestBody ProductVariantRequestDTO request) {

        return productVariantService.updateVariant(id, request);
    }

    // Delete Variant
    @DeleteMapping("/{id}")
    public String deleteVariant(@PathVariable Long id) {

        productVariantService.deleteVariant(id);

        return "Product Variant deleted successfully.";
    }
}