package com.pradnyasanskar.webstore.dto;

import java.math.BigDecimal;

public class CouponApplyRequestDTO {

    private String couponCode;

    private Long userId;

    private BigDecimal orderAmount;

    public CouponApplyRequestDTO() {
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }
}