package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.OrderRequestDTO;
import com.pradnyasanskar.webstore.dto.OrderResponseDTO;
import com.pradnyasanskar.webstore.entity.OrderStatus;
import com.pradnyasanskar.webstore.entity.PaymentStatus;

import java.util.List;

public interface OrderService {

    // Create Order
    OrderResponseDTO createOrder(OrderRequestDTO request);

    // Get All Orders
    List<OrderResponseDTO> getAllOrders();

    // Get Order By ID
    OrderResponseDTO getOrderById(Long orderId);

    // Get Order By Order Number
    OrderResponseDTO getOrderByOrderNumber(String orderNumber);

    // Get Orders of User
    List<OrderResponseDTO> getOrdersByUser(Long userId);

    // Get Orders By Status
    List<OrderResponseDTO> getOrdersByStatus(OrderStatus status);

    // Get Orders By Payment Status
    List<OrderResponseDTO> getOrdersByPaymentStatus(PaymentStatus status);

    // Update Order Status
    OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus status);

    // Update Payment Status
    OrderResponseDTO updatePaymentStatus(Long orderId, PaymentStatus status);

    // Cancel Order
    void cancelOrder(Long orderId);
}