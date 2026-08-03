package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.CouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponUsageRepository extends JpaRepository<CouponUsage, Long> {

    // All usages of a coupon
    List<CouponUsage> findByCouponCouponId(Long couponId);

    // All coupons used by a user
    List<CouponUsage> findByUserUserId(Long userId);

    // Usage count of one coupon by one user
    long countByCouponCouponIdAndUserUserId(Long couponId, Long userId);

    // Usage history of an order
    List<CouponUsage> findByOrderOrderId(Long orderId);

}