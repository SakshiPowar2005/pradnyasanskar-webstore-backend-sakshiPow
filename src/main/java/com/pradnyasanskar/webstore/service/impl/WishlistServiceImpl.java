package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.WishlistRequestDTO;
import com.pradnyasanskar.webstore.dto.WishlistResponseDTO;
import com.pradnyasanskar.webstore.entity.ProductVariant;
import com.pradnyasanskar.webstore.entity.User;
import com.pradnyasanskar.webstore.entity.Wishlist;
import com.pradnyasanskar.webstore.repository.ProductVariantRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.repository.WishlistRepository;
import com.pradnyasanskar.webstore.service.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;

    public WishlistServiceImpl(
            WishlistRepository wishlistRepository,
            UserRepository userRepository,
            ProductVariantRepository productVariantRepository) {

        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.productVariantRepository = productVariantRepository;
    }

    // ============================================================
    // ADD PRODUCT TO WISHLIST
    // ============================================================

    @Override
    public WishlistResponseDTO addToWishlist(
            WishlistRequestDTO request) {

        if (wishlistRepository.existsByUser_UserIdAndProductVariant_VariantId(
                request.getUserId(),
                request.getVariantId())) {

            throw new RuntimeException(
                    "Product already exists in wishlist.");
        }

        User user = userRepository.findById(
                        request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        ProductVariant variant =
                productVariantRepository.findById(
                                request.getVariantId())
                        .orElseThrow(() ->
                                new RuntimeException("Product Variant not found."));

        Wishlist wishlist = new Wishlist();

        wishlist.setUser(user);

        wishlist.setProductVariant(variant);

        Wishlist saved =
                wishlistRepository.save(wishlist);

        return mapToResponse(saved);
    }
    // ============================================================
    // GET WISHLIST BY USER
    // ============================================================

    @Override
    public List<WishlistResponseDTO> getWishlistByUser(
            Long userId) {

        return wishlistRepository.findByUser_UserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // REMOVE PRODUCT FROM WISHLIST
    // ============================================================

    @Override
    public void removeFromWishlist(
            Long userId,
            Long variantId) {

        Wishlist wishlist =
                wishlistRepository
                        .findByUser_UserIdAndProductVariant_VariantId(
                                userId,
                                variantId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Wishlist item not found."));

        wishlistRepository.delete(wishlist);
    }

    // ============================================================
    // CHECK PRODUCT EXISTS IN WISHLIST
    // ============================================================

    @Override
    public boolean existsInWishlist(
            Long userId,
            Long variantId) {

        return wishlistRepository
                .existsByUser_UserIdAndProductVariant_VariantId(
                        userId,
                        variantId);
    }

    // ============================================================
    // MAP ENTITY TO DTO
    // ============================================================

    private WishlistResponseDTO mapToResponse(
            Wishlist wishlist) {

        WishlistResponseDTO response =
                new WishlistResponseDTO();

        response.setWishlistId(
                wishlist.getWishlistId());

        response.setUserId(
                wishlist.getUser().getUserId());

        response.setVariantId(
                wishlist.getProductVariant().getVariantId());

        response.setVariantName(
                wishlist.getProductVariant().getVariantName());

        response.setProductName(
                wishlist.getProductVariant()
                        .getProduct()
                        .getProductName());

        response.setCreatedAt(
                wishlist.getCreatedAt());

        return response;
    }

}