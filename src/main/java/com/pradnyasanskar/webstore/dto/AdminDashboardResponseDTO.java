package com.pradnyasanskar.webstore.dto;

import java.math.BigDecimal;

public class AdminDashboardResponseDTO {

    // ==============================
    // User Statistics
    // ==============================

    private Long totalUsers;
    private Long activeUsers;

    // ==============================
    // Product Statistics
    // ==============================

    private Long totalProducts;

    // ==============================
    // Category Statistics
    // ==============================

    private Long totalCategories;

    // ==============================
    // Order Statistics
    // ==============================

    private Long totalOrders;
    private Long pendingOrders;
    private Long completedOrders;
    private Long cancelledOrders;

    // ==============================
    // Revenue
    // ==============================

    private BigDecimal totalRevenue;

    // ==============================
    // Coupon Statistics
    // ==============================

    private Long totalCoupons;
    private Long activeCoupons;

    // ==============================
    // Review Statistics
    // ==============================

    private Long totalReviews;
    private Double averageRating;

    public AdminDashboardResponseDTO() {
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(Long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public Long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Long getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(Long totalCategories) {
        this.totalCategories = totalCategories;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Long getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(Long pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public Long getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(Long completedOrders) {
        this.completedOrders = completedOrders;
    }

    public Long getCancelledOrders() {
        return cancelledOrders;
    }

    public void setCancelledOrders(Long cancelledOrders) {
        this.cancelledOrders = cancelledOrders;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getTotalCoupons() {
        return totalCoupons;
    }

    public void setTotalCoupons(Long totalCoupons) {
        this.totalCoupons = totalCoupons;
    }

    public Long getActiveCoupons() {
        return activeCoupons;
    }

    public void setActiveCoupons(Long activeCoupons) {
        this.activeCoupons = activeCoupons;
    }

    public Long getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Long totalReviews) {
        this.totalReviews = totalReviews;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}