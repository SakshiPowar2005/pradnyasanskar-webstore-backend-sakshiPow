package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.*;

public interface CartService {

    // ==========================================
    // ADD ITEM TO CART
    // ==========================================

    CartResponseDTO addToCart(
            AddToCartRequestDTO request);

    // ==========================================
    // GET USER CART
    // ==========================================

    CartResponseDTO getCartByUser(
            Long userId);

    // ==========================================
    // UPDATE CART ITEM
    // ==========================================

    CartResponseDTO updateCartItem(
            Long cartItemId,
            UpdateCartItemRequestDTO request);

    // ==========================================
    // REMOVE CART ITEM
    // ==========================================

    void removeCartItem(
            Long cartItemId);

    // ==========================================
    // CLEAR CART
    // ==========================================

    void clearCart(
            Long userId);

    // ==========================================
    // CHECKOUT
    // ==========================================

    OrderResponseDTO checkout(
            Long userId,
            CheckoutRequestDTO request);

}