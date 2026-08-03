package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.Order;
import com.pradnyasanskar.webstore.entity.OrderStatus;
import com.pradnyasanskar.webstore.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByUserUserId(Long userId);

    List<Order> findByOrderStatus(OrderStatus status);

    List<Order> findByPaymentStatus(PaymentStatus status);

    boolean existsByOrderNumber(String orderNumber);

    long countByOrderStatus(OrderStatus status);

    // ============================================================
// Total Revenue
// ============================================================

    @Query("""
SELECT COALESCE(SUM(o.totalAmount), 0)
FROM Order o
""")
    BigDecimal getTotalRevenue();
}