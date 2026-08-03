package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.CouponApplyRequestDTO;
import com.pradnyasanskar.webstore.dto.CouponRequestDTO;
import com.pradnyasanskar.webstore.dto.CouponResponseDTO;
import com.pradnyasanskar.webstore.dto.CouponValidationResponseDTO;

import java.util.List;

public interface CouponService {

    // ==========================
    // CRUD Operations
    // ==========================

    // Create Coupon
    CouponResponseDTO createCoupon(CouponRequestDTO request);

    // Get All Coupons
    List<CouponResponseDTO> getAllCoupons();

    // Get Coupon By ID
    CouponResponseDTO getCouponById(Long couponId);

    // Get Coupon By Code
    CouponResponseDTO getCouponByCode(String couponCode);

    // Get Active Coupons
    List<CouponResponseDTO> getActiveCoupons();

    // Update Coupon
    CouponResponseDTO updateCoupon(Long couponId,
                                   CouponRequestDTO request);

    // Activate Coupon
    CouponResponseDTO activateCoupon(Long couponId);

    // Deactivate Coupon
    CouponResponseDTO deactivateCoupon(Long couponId);

    // Delete Coupon
    void deleteCoupon(Long couponId);

    // ==========================
    // Coupon Validation
    // ==========================

    // Validate a coupon without applying it
    CouponValidationResponseDTO validateCoupon(
            CouponApplyRequestDTO request);

    // Validate and apply a coupon
    CouponValidationResponseDTO applyCoupon(
            CouponApplyRequestDTO request);

}