package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.ProductVariantRequestDTO;
import com.pradnyasanskar.webstore.dto.ProductVariantResponseDTO;

import java.util.List;

public interface ProductVariantService {

    ProductVariantResponseDTO createVariant(ProductVariantRequestDTO request);

    List<ProductVariantResponseDTO> getAllVariants();

    ProductVariantResponseDTO getVariantById(Long variantId);

    List<ProductVariantResponseDTO> getVariantsByProduct(Long productId);

    List<ProductVariantResponseDTO> getActiveVariants();

    ProductVariantResponseDTO updateVariant(Long variantId,
                                            ProductVariantRequestDTO request);

    void deleteVariant(Long variantId);
}