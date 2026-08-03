package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.AdminDashboardResponseDTO;
import com.pradnyasanskar.webstore.entity.OrderStatus;
import com.pradnyasanskar.webstore.enums.AccountStatus;
import com.pradnyasanskar.webstore.repository.CategoryRepository;
import com.pradnyasanskar.webstore.repository.CouponRepository;
import com.pradnyasanskar.webstore.repository.OrderRepository;
import com.pradnyasanskar.webstore.repository.ProductRepository;
import com.pradnyasanskar.webstore.repository.ProductReviewRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.service.AdminDashboardService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;
    private final CouponRepository couponRepository;
    private final ProductReviewRepository productReviewRepository;

    public AdminDashboardServiceImpl(
            UserRepository userRepository,
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            OrderRepository orderRepository,
            CouponRepository couponRepository,
            ProductReviewRepository productReviewRepository) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.orderRepository = orderRepository;
        this.couponRepository = couponRepository;
        this.productReviewRepository = productReviewRepository;
    }


    @Override
    public AdminDashboardResponseDTO getDashboardSummary() {

        AdminDashboardResponseDTO dto = new AdminDashboardResponseDTO();

        // ============================================================
        // Users
        // ============================================================

        dto.setTotalUsers(userRepository.count());

        dto.setActiveUsers(
                userRepository.countByAccountStatus(AccountStatus.ACTIVE)
        );

        // ============================================================
        // Products
        // ============================================================

        dto.setTotalProducts(productRepository.count());

        // ============================================================
        // Categories
        // ============================================================

        dto.setTotalCategories(categoryRepository.count());

        // ============================================================
        // Orders
        // ============================================================

        dto.setTotalOrders(orderRepository.count());

        dto.setPendingOrders(
                orderRepository.countByOrderStatus(OrderStatus.PENDING)
        );

        dto.setCompletedOrders(
                orderRepository.countByOrderStatus(OrderStatus.DELIVERED)
        );

        dto.setCancelledOrders(
                orderRepository.countByOrderStatus(OrderStatus.CANCELLED)
        );

        // ============================================================
        // Revenue
        // ============================================================

        BigDecimal revenue = orderRepository.getTotalRevenue();

        dto.setTotalRevenue(
                revenue == null ? BigDecimal.ZERO : revenue
        );

        // ============================================================
        // Coupons
        // ============================================================

        dto.setTotalCoupons(couponRepository.count());

        dto.setActiveCoupons(
                couponRepository.countByIsActiveTrue()
        );

        // ============================================================
        // Reviews
        // ============================================================

        dto.setTotalReviews(productReviewRepository.count());

        Double rating = productReviewRepository.getAverageRating();

        dto.setAverageRating(
                rating == null ? 0.0 : rating
        );

        return dto;
    }
}