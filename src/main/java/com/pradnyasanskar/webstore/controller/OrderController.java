package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.OrderRequestDTO;
import com.pradnyasanskar.webstore.dto.OrderResponseDTO;
import com.pradnyasanskar.webstore.entity.OrderStatus;
import com.pradnyasanskar.webstore.entity.PaymentStatus;
import com.pradnyasanskar.webstore.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // =====================================================
    // Create Order
    // =====================================================

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(
            @RequestBody OrderRequestDTO request) {

        return new ResponseEntity<>(
                orderService.createOrder(request),
                HttpStatus.CREATED);
    }

    // =====================================================
    // Get All Orders
    // =====================================================

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders());
    }

    // =====================================================
    // Get Order By ID
    // =====================================================

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderById(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                orderService.getOrderById(orderId));
    }

    // =====================================================
    // Get Order By Order Number
    // =====================================================

    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<OrderResponseDTO> getOrderByOrderNumber(
            @PathVariable String orderNumber) {

        return ResponseEntity.ok(
                orderService.getOrderByOrderNumber(orderNumber));
    }

    // =====================================================
    // Get Orders By User
    // =====================================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                orderService.getOrdersByUser(userId));
    }

    // =====================================================
    // Get Orders By Status
    // =====================================================

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByStatus(
            @PathVariable OrderStatus status) {

        return ResponseEntity.ok(
                orderService.getOrdersByStatus(status));
    }

    // =====================================================
    // Get Orders By Payment Status
    // =====================================================

    @GetMapping("/payment-status/{status}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByPaymentStatus(
            @PathVariable PaymentStatus status) {

        return ResponseEntity.ok(
                orderService.getOrdersByPaymentStatus(status));
    }

    // =====================================================
    // Update Order Status
    // =====================================================

    @PutMapping("/{orderId}/status/{status}")
    public ResponseEntity<OrderResponseDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @PathVariable OrderStatus status) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(orderId, status));
    }

    // =====================================================
    // Update Payment Status
    // =====================================================

    @PutMapping("/{orderId}/payment-status/{status}")
    public ResponseEntity<OrderResponseDTO> updatePaymentStatus(
            @PathVariable Long orderId,
            @PathVariable PaymentStatus status) {

        return ResponseEntity.ok(
                orderService.updatePaymentStatus(orderId, status));
    }

    // =====================================================
    // Cancel Order
    // =====================================================

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(
            @PathVariable Long orderId) {

        orderService.cancelOrder(orderId);

        return ResponseEntity.ok("Order cancelled successfully.");
    }

}