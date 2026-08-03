package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.WishlistRequestDTO;
import com.pradnyasanskar.webstore.dto.WishlistResponseDTO;

import java.util.List;

public interface WishlistService {

    // ============================================================
    // ADD PRODUCT TO WISHLIST
    // ============================================================

    WishlistResponseDTO addToWishlist(
            WishlistRequestDTO request);

    // ============================================================
    // GET WISHLIST BY USER
    // ============================================================

    List<WishlistResponseDTO> getWishlistByUser(
            Long userId);

    // ============================================================
    // REMOVE PRODUCT FROM WISHLIST
    // ============================================================

    void removeFromWishlist(
            Long userId,
            Long variantId);

    // ============================================================
    // CHECK PRODUCT EXISTS IN WISHLIST
    // ============================================================

    boolean existsInWishlist(
            Long userId,
            Long variantId);
}