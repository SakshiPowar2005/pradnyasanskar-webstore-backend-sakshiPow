package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    // Find coupon by code
    Optional<Coupon> findByCouponCode(String couponCode);

    // Check duplicate coupon code
    boolean existsByCouponCode(String couponCode);


    long countByIsActiveTrue();

    // Active coupons
    List<Coupon> findByIsActiveTrue();

    // Coupons that haven't expired
    List<Coupon> findByExpiryDateAfter(LocalDateTime date);

    // Active & valid coupon
    Optional<Coupon> findByCouponCodeAndIsActiveTrue(String couponCode);

}