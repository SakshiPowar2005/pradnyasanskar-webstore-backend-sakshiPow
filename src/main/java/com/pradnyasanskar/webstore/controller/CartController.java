package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.*;
import com.pradnyasanskar.webstore.service.CartService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // =====================================================
    // ADD TO CART
    // =====================================================

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(
            @RequestBody AddToCartRequestDTO request) {

        return new ResponseEntity<>(
                cartService.addToCart(request),
                HttpStatus.CREATED);
    }

    // =====================================================
    // GET USER CART
    // =====================================================

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDTO> getCart(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                cartService.getCartByUser(userId));
    }

    // =====================================================
    // UPDATE CART ITEM
    // =====================================================

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartResponseDTO> updateCartItem(
            @PathVariable Long cartItemId,
            @RequestBody UpdateCartItemRequestDTO request) {

        return ResponseEntity.ok(
                cartService.updateCartItem(
                        cartItemId,
                        request));
    }

    // =====================================================
    // REMOVE CART ITEM
    // =====================================================

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<String> removeCartItem(
            @PathVariable Long cartItemId) {

        cartService.removeCartItem(cartItemId);

        return ResponseEntity.ok(
                "Cart item removed successfully.");
    }

    // =====================================================
    // CLEAR CART
    // =====================================================

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<String> clearCart(
            @PathVariable Long userId) {

        cartService.clearCart(userId);

        return ResponseEntity.ok(
                "Cart cleared successfully.");
    }

    // =====================================================
    // CHECKOUT
    // =====================================================

    @PostMapping("/{userId}/checkout")
    public ResponseEntity<OrderResponseDTO> checkout(
            @PathVariable Long userId,
            @RequestBody CheckoutRequestDTO request) {

        return ResponseEntity.ok(
                cartService.checkout(userId, request));
    }
}