package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.WishlistRequestDTO;
import com.pradnyasanskar.webstore.dto.WishlistResponseDTO;
import com.pradnyasanskar.webstore.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    // ============================================================
    // ADD PRODUCT TO WISHLIST
    // ============================================================

    @PostMapping
    public ResponseEntity<WishlistResponseDTO> addToWishlist(
            @RequestBody WishlistRequestDTO request) {

        return new ResponseEntity<>(
                wishlistService.addToWishlist(request),
                HttpStatus.CREATED);
    }

    // ============================================================
    // GET USER WISHLIST
    // ============================================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WishlistResponseDTO>> getWishlistByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                wishlistService.getWishlistByUser(userId));
    }

    // ============================================================
    // CHECK PRODUCT EXISTS IN WISHLIST
    // ============================================================

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsInWishlist(
            @RequestParam Long userId,
            @RequestParam Long variantId) {

        return ResponseEntity.ok(
                wishlistService.existsInWishlist(
                        userId,
                        variantId));
    }

    // ============================================================
    // REMOVE PRODUCT FROM WISHLIST
    // ============================================================

    @DeleteMapping
    public ResponseEntity<String> removeFromWishlist(
            @RequestParam Long userId,
            @RequestParam Long variantId) {

        wishlistService.removeFromWishlist(
                userId,
                variantId);

        return ResponseEntity.ok(
                "Product removed from wishlist successfully.");
    }

}