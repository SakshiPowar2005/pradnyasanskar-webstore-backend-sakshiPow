package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.ProductVariantRequestDTO;
import com.pradnyasanskar.webstore.dto.ProductVariantResponseDTO;
import com.pradnyasanskar.webstore.entity.Product;
import com.pradnyasanskar.webstore.entity.ProductVariant;
import com.pradnyasanskar.webstore.repository.ProductRepository;
import com.pradnyasanskar.webstore.repository.ProductVariantRepository;
import com.pradnyasanskar.webstore.service.ProductVariantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository variantRepository;
    private final ProductRepository productRepository;

    public ProductVariantServiceImpl(ProductVariantRepository variantRepository,
                                     ProductRepository productRepository) {
        this.variantRepository = variantRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductVariantResponseDTO createVariant(ProductVariantRequestDTO request) {

        if (variantRepository.existsBySku(request.getSku())) {
            throw new RuntimeException("SKU already exists.");
        }

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found."));

        ProductVariant variant = new ProductVariant();

        variant.setProduct(product);
        variant.setSku(request.getSku());
        variant.setVariantName(request.getVariantName());
        variant.setStrength(request.getStrength());
        variant.setPackSize(request.getPackSize());
        variant.setUnitOfMeasure(request.getUnitOfMeasure());
        variant.setMrp(request.getMrp());
        variant.setSellingPrice(request.getSellingPrice());
        variant.setGstPercentage(request.getGstPercentage());
        variant.setReorderLevel(request.getReorderLevel());
        variant.setWeight(request.getWeight());
        variant.setDimensions(request.getDimensions());
        variant.setIsActive(request.getIsActive());
        if (request.getIsActive() == null) {
            variant.setIsActive(true);
        } else {
            variant.setIsActive(request.getIsActive());
        }
        ProductVariant saved = variantRepository.save(variant);

        return mapToResponse(saved);
    }

    @Override
    public List<ProductVariantResponseDTO> getAllVariants() {

        return variantRepository.findByIsActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductVariantResponseDTO getVariantById(Long variantId) {

        ProductVariant variant =
                variantRepository.findByVariantIdAndIsActiveTrue(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found."));

        return mapToResponse(variant);
    }

    @Override
    public List<ProductVariantResponseDTO> getVariantsByProduct(Long productId) {

        return variantRepository.findByProductProductIdAndIsActiveTrue(productId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductVariantResponseDTO> getActiveVariants() {

        return variantRepository.findByIsActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductVariantResponseDTO updateVariant(Long variantId,
                                                   ProductVariantRequestDTO request) {

        ProductVariant variant = variantRepository.findByVariantIdAndIsActiveTrue(variantId)
                .orElseThrow(() -> new RuntimeException("Variant not found."));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found."));

        variant.setProduct(product);
        variant.setSku(request.getSku());
        variant.setVariantName(request.getVariantName());
        variant.setStrength(request.getStrength());
        variant.setPackSize(request.getPackSize());
        variant.setUnitOfMeasure(request.getUnitOfMeasure());
        variant.setMrp(request.getMrp());
        variant.setSellingPrice(request.getSellingPrice());
        variant.setGstPercentage(request.getGstPercentage());
        variant.setReorderLevel(request.getReorderLevel());
        variant.setWeight(request.getWeight());
        variant.setDimensions(request.getDimensions());
        variant.setIsActive(request.getIsActive());

        ProductVariant updated = variantRepository.save(variant);

        return mapToResponse(updated);
    }

    @Override
    public void deleteVariant(Long variantId) {

        ProductVariant variant =
                variantRepository.findById(variantId)
                        .orElseThrow(() ->
                                new RuntimeException("Variant not found."));

        if (!variant.getIsActive()) {
            throw new RuntimeException("Variant already deleted.");
        }

        variant.setIsActive(false);

        variantRepository.save(variant);
    }


    private ProductVariantResponseDTO mapToResponse(ProductVariant variant) {

        ProductVariantResponseDTO response = new ProductVariantResponseDTO();

        response.setVariantId(variant.getVariantId());

        response.setProductId(variant.getProduct().getProductId());
        response.setProductName(variant.getProduct().getProductName());

        response.setSku(variant.getSku());
        response.setVariantName(variant.getVariantName());
        response.setStrength(variant.getStrength());
        response.setPackSize(variant.getPackSize());
        response.setUnitOfMeasure(variant.getUnitOfMeasure());

        response.setMrp(variant.getMrp());
        response.setSellingPrice(variant.getSellingPrice());
        response.setGstPercentage(variant.getGstPercentage());

        response.setReorderLevel(variant.getReorderLevel());

        response.setWeight(variant.getWeight());
        response.setDimensions(variant.getDimensions());

        response.setIsActive(variant.getIsActive());

        return response;
    }
}