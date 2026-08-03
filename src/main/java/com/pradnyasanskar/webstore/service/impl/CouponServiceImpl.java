package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.CouponApplyRequestDTO;
import com.pradnyasanskar.webstore.dto.CouponRequestDTO;
import com.pradnyasanskar.webstore.dto.CouponResponseDTO;
import com.pradnyasanskar.webstore.dto.CouponValidationResponseDTO;

import com.pradnyasanskar.webstore.entity.Coupon;
import com.pradnyasanskar.webstore.entity.User;

import com.pradnyasanskar.webstore.repository.CouponRepository;
import com.pradnyasanskar.webstore.repository.CouponUsageRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;

import com.pradnyasanskar.webstore.service.CouponService;

import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final CouponUsageRepository couponUsageRepository;

    public CouponServiceImpl(
            CouponRepository couponRepository,
            UserRepository userRepository,
            CouponUsageRepository couponUsageRepository) {

        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
        this.couponUsageRepository = couponUsageRepository;
    }

    // =====================================================
    // CREATE COUPON
    // =====================================================

    @Override
    public CouponResponseDTO createCoupon(CouponRequestDTO request) {

        if (couponRepository.existsByCouponCode(request.getCouponCode())) {
            throw new RuntimeException("Coupon code already exists.");
        }

        if (request.getDiscountValue()
                .compareTo(BigDecimal.ZERO) <= 0) {

            throw new RuntimeException(
                    "Discount value must be greater than zero.");
        }

        if (request.getStartDate()
                .isAfter(request.getExpiryDate())) {

            throw new RuntimeException(
                    "Expiry date must be after start date.");
        }

        Coupon coupon = new Coupon();

        coupon.setCouponCode(request.getCouponCode());

        coupon.setDescription(request.getDescription());

        coupon.setCouponType(request.getDiscountType());

        coupon.setDiscountValue(request.getDiscountValue());

        coupon.setMinimumOrderAmount(
                request.getMinimumOrderAmount());

        coupon.setMaximumDiscount(
                request.getMaximumDiscount());

        coupon.setUsageLimit(
                request.getUsageLimit());

        coupon.setPerUserLimit(
                request.getPerUserLimit());

        coupon.setStartDate(
                request.getStartDate());

        coupon.setExpiryDate(
                request.getExpiryDate());

        if (request.getIsActive() == null)
            coupon.setIsActive(true);
        else
            coupon.setIsActive(request.getIsActive());

        coupon.setUsedCount(0);

        if (request.getCreatedBy() != null) {

            User user = userRepository.findById(
                            request.getCreatedBy())
                    .orElseThrow(() ->
                            new RuntimeException("User not found."));

            coupon.setCreatedBy(user);
        }

        Coupon savedCoupon =
                couponRepository.save(coupon);

        return mapToResponse(savedCoupon);
    }
    // =====================================================
    // GET ALL COUPONS
    // =====================================================

    @Override
    public List<CouponResponseDTO> getAllCoupons() {

        return couponRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =====================================================
    // GET COUPON BY ID
    // =====================================================

    @Override
    public CouponResponseDTO getCouponById(Long couponId) {

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() ->
                        new RuntimeException("Coupon not found."));

        return mapToResponse(coupon);
    }

    // =====================================================
    // GET COUPON BY CODE
    // =====================================================

    @Override
    public CouponResponseDTO getCouponByCode(String couponCode) {

        Coupon coupon = couponRepository
                .findByCouponCode(couponCode)
                .orElseThrow(() ->
                        new RuntimeException("Coupon not found."));

        return mapToResponse(coupon);
    }

    // =====================================================
    // GET ACTIVE COUPONS
    // =====================================================

    @Override
    public List<CouponResponseDTO> getActiveCoupons() {

        return couponRepository.findByIsActiveTrue()
                .stream()
                .filter(coupon ->
                        coupon.getStartDate().isBefore(LocalDateTime.now())
                                &&
                                coupon.getExpiryDate().isAfter(LocalDateTime.now()))
                .map(this::mapToResponse)
                .toList();
    }
    // =====================================================
    // UPDATE COUPON
    // =====================================================

    @Override
    public CouponResponseDTO updateCoupon(Long couponId,
                                          CouponRequestDTO request) {

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() ->
                        new RuntimeException("Coupon not found."));

        // Duplicate coupon code validation
        if (!coupon.getCouponCode().equals(request.getCouponCode())
                && couponRepository.existsByCouponCode(request.getCouponCode())) {

            throw new RuntimeException("Coupon code already exists.");
        }

        // Discount validation
        if (request.getDiscountValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException(
                    "Discount value must be greater than zero.");
        }

        // Date validation
        if (request.getStartDate().isAfter(request.getExpiryDate())) {
            throw new RuntimeException(
                    "Expiry date must be after start date.");
        }

        coupon.setCouponCode(request.getCouponCode());

        coupon.setDescription(request.getDescription());

        coupon.setCouponType(request.getDiscountType());

        coupon.setDiscountValue(request.getDiscountValue());

        coupon.setMinimumOrderAmount(
                request.getMinimumOrderAmount());

        coupon.setMaximumDiscount(
                request.getMaximumDiscount());

        coupon.setUsageLimit(
                request.getUsageLimit());

        coupon.setPerUserLimit(
                request.getPerUserLimit());

        coupon.setStartDate(
                request.getStartDate());

        coupon.setExpiryDate(
                request.getExpiryDate());

        coupon.setIsActive(
                request.getIsActive());

        Coupon updatedCoupon =
                couponRepository.save(coupon);

        return mapToResponse(updatedCoupon);
    }

    // =====================================================
    // ACTIVATE COUPON
    // =====================================================

    @Override
    public CouponResponseDTO activateCoupon(Long couponId) {

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() ->
                        new RuntimeException("Coupon not found."));

        coupon.setIsActive(true);

        return mapToResponse(
                couponRepository.save(coupon));
    }

    // =====================================================
    // DEACTIVATE COUPON
    // =====================================================

    @Override
    public CouponResponseDTO deactivateCoupon(Long couponId) {

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() ->
                        new RuntimeException("Coupon not found."));

        coupon.setIsActive(false);

        return mapToResponse(
                couponRepository.save(coupon));
    }

    // =====================================================
    // DELETE COUPON
    // =====================================================

    @Override
    public void deleteCoupon(Long couponId) {

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() ->
                        new RuntimeException("Coupon not found."));

        couponRepository.delete(coupon);
    }
    // =====================================================
// VALIDATE COUPON
// =====================================================

    @Override
    public CouponValidationResponseDTO validateCoupon(
            CouponApplyRequestDTO request) {

        Coupon coupon = couponRepository
                .findByCouponCode(request.getCouponCode())
                .orElseThrow(() ->
                        new RuntimeException("Invalid coupon code."));

        CouponValidationResponseDTO response =
                new CouponValidationResponseDTO();

        // -------------------------------
        // Active Check
        // -------------------------------

        if (!Boolean.TRUE.equals(coupon.getIsActive())) {

            response.setValid(false);
            response.setMessage("Coupon is inactive.");

            return response;
        }

        LocalDateTime now = LocalDateTime.now();

        // -------------------------------
        // Start Date Check
        // -------------------------------

        if (now.isBefore(coupon.getStartDate())) {

            response.setValid(false);
            response.setMessage("Coupon is not active yet.");

            return response;
        }

        // -------------------------------
        // Expiry Check
        // -------------------------------

        if (now.isAfter(coupon.getExpiryDate())) {

            response.setValid(false);
            response.setMessage("Coupon has expired.");

            return response;
        }

        // -------------------------------
        // Minimum Order Check
        // -------------------------------

        if (request.getOrderAmount().compareTo(
                coupon.getMinimumOrderAmount()) < 0) {

            response.setValid(false);

            response.setMessage(
                    "Minimum order amount is "
                            + coupon.getMinimumOrderAmount());

            return response;
        }

        // -------------------------------
        // Usage Limit Check
        // -------------------------------

        if (coupon.getUsageLimit() != null &&
                coupon.getUsedCount() >= coupon.getUsageLimit()) {

            response.setValid(false);
            response.setMessage("Coupon usage limit exceeded.");

            return response;
        }
        // -------------------------------
        // Per User Limit Check
        // -------------------------------

        long userUsageCount =
                couponUsageRepository.countByCouponCouponIdAndUserUserId(
                        coupon.getCouponId(),
                        request.getUserId());

        if (userUsageCount >= coupon.getPerUserLimit()) {

            response.setValid(false);
            response.setMessage(
                    "You have already used this coupon.");

            return response;
        }

        // -------------------------------
        // Calculate Discount
        // -------------------------------

        BigDecimal discount;

        switch (coupon.getCouponType()) {

            case PERCENTAGE:

                discount = request.getOrderAmount()
                        .multiply(coupon.getDiscountValue())
                        .divide(BigDecimal.valueOf(100));

                if (coupon.getMaximumDiscount() != null
                        &&
                        discount.compareTo(
                                coupon.getMaximumDiscount()) > 0) {

                    discount = coupon.getMaximumDiscount();
                }

                break;

            case FIXED:

                discount = coupon.getDiscountValue();

                if (discount.compareTo(
                        request.getOrderAmount()) > 0) {

                    discount = request.getOrderAmount();
                }

                break;

            default:

                throw new RuntimeException(
                        "Unsupported coupon type.");
        }

        BigDecimal finalAmount =
                request.getOrderAmount().subtract(discount);

        response.setValid(true);

        response.setMessage(
                "Coupon applied successfully.");

        response.setDiscountAmount(discount);

        response.setFinalAmount(finalAmount);

        return response;
    }
    // =====================================================
    // DTO MAPPING
    // =====================================================

    private CouponResponseDTO mapToResponse(Coupon coupon) {

        CouponResponseDTO dto = new CouponResponseDTO();

        dto.setCouponId(coupon.getCouponId());

        dto.setCouponCode(coupon.getCouponCode());

        dto.setDescription(coupon.getDescription());

        dto.setDiscountType(coupon.getCouponType());

        dto.setDiscountValue(coupon.getDiscountValue());

        dto.setMinimumOrderAmount(
                coupon.getMinimumOrderAmount());

        dto.setMaximumDiscount(
                coupon.getMaximumDiscount());

        dto.setUsageLimit(
                coupon.getUsageLimit());

        dto.setPerUserLimit(
                coupon.getPerUserLimit());

        dto.setUsedCount(
                coupon.getUsedCount());

        dto.setStartDate(
                coupon.getStartDate());

        dto.setExpiryDate(
                coupon.getExpiryDate());

        dto.setIsActive(
                coupon.getIsActive());

        if (coupon.getCreatedBy() != null) {
            dto.setCreatedBy(
                    coupon.getCreatedBy().getUserId());
        }

        dto.setCreatedAt(
                coupon.getCreatedAt());

        dto.setUpdatedAt(
                coupon.getUpdatedAt());

        return dto;
    }
    @Override
    public CouponValidationResponseDTO applyCoupon(
            CouponApplyRequestDTO request) {

        // For now, simply validate the coupon
        return validateCoupon(request);
    }

}