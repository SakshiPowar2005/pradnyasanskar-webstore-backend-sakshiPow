package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.CouponApplyRequestDTO;
import com.pradnyasanskar.webstore.dto.CouponRequestDTO;
import com.pradnyasanskar.webstore.dto.CouponResponseDTO;
import com.pradnyasanskar.webstore.dto.CouponValidationResponseDTO;
import com.pradnyasanskar.webstore.service.CouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin(origins = "*")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    // ============================================
    // CREATE COUPON
    // ============================================

    @PostMapping
    public ResponseEntity<CouponResponseDTO> createCoupon(
            @RequestBody CouponRequestDTO request) {

        return new ResponseEntity<>(
                couponService.createCoupon(request),
                HttpStatus.CREATED);
    }

    // ============================================
    // GET ALL COUPONS
    // ============================================

    @GetMapping
    public ResponseEntity<List<CouponResponseDTO>> getAllCoupons() {

        return ResponseEntity.ok(
                couponService.getAllCoupons());
    }

    // ============================================
    // GET COUPON BY ID
    // ============================================

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> getCouponById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                couponService.getCouponById(id));
    }

    // ============================================
    // GET COUPON BY CODE
    // ============================================

    @GetMapping("/code/{code}")
    public ResponseEntity<CouponResponseDTO> getCouponByCode(
            @PathVariable String code) {

        return ResponseEntity.ok(
                couponService.getCouponByCode(code));
    }

    // ============================================
    // GET ACTIVE COUPONS
    // ============================================

    @GetMapping("/active")
    public ResponseEntity<List<CouponResponseDTO>> getActiveCoupons() {

        return ResponseEntity.ok(
                couponService.getActiveCoupons());
    }

    // ============================================
    // UPDATE COUPON
    // ============================================

    @PutMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> updateCoupon(
            @PathVariable Long id,
            @RequestBody CouponRequestDTO request) {

        return ResponseEntity.ok(
                couponService.updateCoupon(id, request));
    }

    // ============================================
    // ACTIVATE COUPON
    // ============================================

    @PatchMapping("/{id}/activate")
    public ResponseEntity<CouponResponseDTO> activateCoupon(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                couponService.activateCoupon(id));
    }

    // ============================================
    // DEACTIVATE COUPON
    // ============================================

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<CouponResponseDTO> deactivateCoupon(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                couponService.deactivateCoupon(id));
    }

    // ============================================
    // DELETE COUPON
    // ============================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCoupon(
            @PathVariable Long id) {

        couponService.deleteCoupon(id);

        return ResponseEntity.ok(
                "Coupon deleted successfully.");
    }

    // ============================================
    // VALIDATE COUPON
    // ============================================

    @PostMapping("/validate")
    public ResponseEntity<CouponValidationResponseDTO> validateCoupon(
            @RequestBody CouponApplyRequestDTO request) {

        return ResponseEntity.ok(
                couponService.validateCoupon(request));
    }

    // ============================================
    // APPLY COUPON
    // ============================================

    @PostMapping("/apply")
    public ResponseEntity<CouponValidationResponseDTO> applyCoupon(
            @RequestBody CouponApplyRequestDTO request) {

        return ResponseEntity.ok(
                couponService.applyCoupon(request));
    }

}