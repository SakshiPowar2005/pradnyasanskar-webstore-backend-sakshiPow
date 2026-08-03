package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.*;
import com.pradnyasanskar.webstore.entity.*;
import com.pradnyasanskar.webstore.repository.*;
import com.pradnyasanskar.webstore.service.CartService;
import com.pradnyasanskar.webstore.service.OrderService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;
    private final OrderService orderService;

    public CartServiceImpl(
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            UserRepository userRepository,
            ProductVariantRepository productVariantRepository,
            OrderService orderService) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productVariantRepository = productVariantRepository;
        this.orderService = orderService;
    }

    // =====================================================
    // ADD ITEM TO CART
    // =====================================================

    @Override
    public CartResponseDTO addToCart(AddToCartRequestDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        ProductVariant variant = productVariantRepository
                .findById(request.getVariantId())
                .orElseThrow(() ->
                        new RuntimeException("Product Variant not found."));

        // ---------------------------------------------
        // CREATE CART IF NOT EXISTS
        // ---------------------------------------------

        Cart cart = cartRepository.findByUserUserId(user.getUserId())
                .orElseGet(() -> {

                    Cart newCart = new Cart();

                    newCart.setUser(user);

                    newCart.setCartItems(new ArrayList<>());

                    return cartRepository.save(newCart);
                });

        // ---------------------------------------------
        // CHECK IF PRODUCT ALREADY EXISTS IN CART
        // ---------------------------------------------

        Optional<CartItem> existingItem =
                cartItemRepository
                        .findByCartCartIdAndProductVariantVariantId(
                                cart.getCartId(),
                                variant.getVariantId());

        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();

            int newQuantity =
                    item.getQuantity() + request.getQuantity();

            item.setQuantity(newQuantity);

            item.setSubtotal(
                    item.getUnitPrice()
                            .multiply(BigDecimal.valueOf(newQuantity)));

            cartItemRepository.save(item);

        } else {

            CartItem item = new CartItem();

            item.setCart(cart);

            item.setProductVariant(variant);

            item.setQuantity(request.getQuantity());

            item.setUnitPrice(variant.getSellingPrice());

            item.setSubtotal(
                    variant.getSellingPrice()
                            .multiply(BigDecimal.valueOf(request.getQuantity())));

            cartItemRepository.save(item);
        }

        return getCartByUser(user.getUserId());
    }
    // =====================================================
    // GET USER CART
    // =====================================================

    @Override
    public CartResponseDTO getCartByUser(Long userId) {

        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Cart not found."));

        return mapToCartResponse(cart);
    }
    // =====================================================
    // MAP CART TO RESPONSE DTO
    // =====================================================

    private CartResponseDTO mapToCartResponse(Cart cart) {

        CartResponseDTO response = new CartResponseDTO();

        response.setCartId(cart.getCartId());
        response.setUserId(cart.getUser().getUserId());

        BigDecimal totalAmount = BigDecimal.ZERO;
        Integer totalItems = 0;

        java.util.List<CartItemResponseDTO> itemDTOs = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {

            CartItemResponseDTO itemDTO = new CartItemResponseDTO();

            itemDTO.setCartItemId(cartItem.getCartItemId());

            itemDTO.setVariantId(
                    cartItem.getProductVariant().getVariantId());

            itemDTO.setProductName(
                    cartItem.getProductVariant()
                            .getProduct()
                            .getProductName());

            itemDTO.setSku(
                    cartItem.getProductVariant().getSku());

            itemDTO.setQuantity(
                    cartItem.getQuantity());

            itemDTO.setUnitPrice(
                    cartItem.getUnitPrice());

            itemDTO.setSubtotal(
                    cartItem.getSubtotal());

            itemDTOs.add(itemDTO);

            totalAmount = totalAmount.add(
                    cartItem.getSubtotal());

            totalItems += cartItem.getQuantity();
        }

        response.setItems(itemDTOs);
        response.setTotalAmount(totalAmount);
        response.setTotalItems(totalItems);

        return response;
    }
    // =====================================================
    // UPDATE CART ITEM
    // =====================================================

    @Override
    public CartResponseDTO updateCartItem(
            Long cartItemId,
            UpdateCartItemRequestDTO request) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new RuntimeException("Cart item not found."));

        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new RuntimeException(
                    "Quantity must be greater than zero.");
        }

        cartItem.setQuantity(request.getQuantity());

        BigDecimal subtotal = cartItem.getUnitPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        cartItem.setSubtotal(subtotal);

        cartItemRepository.save(cartItem);

        return getCartByUser(
                cartItem.getCart()
                        .getUser()
                        .getUserId());
    }
    // =====================================================
    // REMOVE CART ITEM
    // =====================================================

    @Override
    public void removeCartItem(Long cartItemId) {

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() ->
                        new RuntimeException("Cart item not found."));

        cartItemRepository.delete(cartItem);
    }

    // =====================================================
    // CLEAR CART
    // =====================================================

    @Override
    public void clearCart(Long userId) {

        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Cart not found."));

        cartItemRepository.deleteByCartCartId(cart.getCartId());
    }
    // =====================================================
// CHECKOUT
// =====================================================

    @Override
    public OrderResponseDTO checkout(
            Long userId,
            CheckoutRequestDTO request) {

        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Cart not found."));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty.");
        }

        OrderRequestDTO orderRequest = new OrderRequestDTO();

        // User
        orderRequest.setUserId(userId);

        // Shipping Details
        orderRequest.setFullName(request.getFullName());
        orderRequest.setMobileNumber(request.getMobileNumber());
        orderRequest.setAddressLine1(request.getAddressLine1());
        orderRequest.setAddressLine2(request.getAddressLine2());
        orderRequest.setLandmark(request.getLandmark());
        orderRequest.setCity(request.getCity());
        orderRequest.setState(request.getState());
        orderRequest.setPostalCode(request.getPostalCode());
        orderRequest.setCountry(request.getCountry());
        orderRequest.setNotes(request.getNotes());

        // Coupon (we'll improve this later)
        orderRequest.setCouponId(null);

        // Convert Cart Items -> Order Items
        java.util.List<OrderItemRequestDTO> orderItems =
                new java.util.ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()) {

            OrderItemRequestDTO item =
                    new OrderItemRequestDTO();

            item.setVariantId(
                    cartItem.getProductVariant().getVariantId());

            item.setQuantity(
                    cartItem.getQuantity());

            orderItems.add(item);
        }

        orderRequest.setItems(orderItems);

        // Create Order
        OrderResponseDTO response =
                orderService.createOrder(orderRequest);

        // Clear Cart
        clearCart(userId);

        return response;
    }
}