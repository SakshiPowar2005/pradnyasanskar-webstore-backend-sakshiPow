package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.PaymentRequestDTO;
import com.pradnyasanskar.webstore.dto.PaymentResponseDTO;
import com.pradnyasanskar.webstore.entity.Order;
import com.pradnyasanskar.webstore.entity.Payment;
import com.pradnyasanskar.webstore.entity.PaymentStatus;
import com.pradnyasanskar.webstore.repository.OrderRepository;
import com.pradnyasanskar.webstore.repository.PaymentRepository;
import com.pradnyasanskar.webstore.service.PaymentService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    // =====================================================
    // CREATE PAYMENT
    // =====================================================

    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() ->
                        new RuntimeException("Order not found."));

        Payment payment = new Payment();

        payment.setOrder(order);

        payment.setPaymentGateway(
                request.getPaymentGateway());

        payment.setPaymentMethod(
                request.getPaymentMethod());

        payment.setAmount(
                request.getAmount());

        if (request.getCurrency() == null ||
                request.getCurrency().isBlank()) {

            payment.setCurrency("INR");

        } else {

            payment.setCurrency(
                    request.getCurrency());
        }

        payment.setPaymentStatus(
                PaymentStatus.PENDING);

        payment.setTransactionId(
                "TXN-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0, 10)
                                .toUpperCase());

        payment.setGatewayOrderId(
                "GW-ORDER-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0, 8)
                                .toUpperCase());

        Payment savedPayment =
                paymentRepository.save(payment);

        return mapToResponse(savedPayment);
    }
    // =====================================================
    // GET ALL PAYMENTS
    // =====================================================

    @Override
    public List<PaymentResponseDTO> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =====================================================
    // GET PAYMENT BY ID
    // =====================================================

    @Override
    public PaymentResponseDTO getPaymentById(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found."));

        return mapToResponse(payment);
    }

    // =====================================================
    // GET PAYMENTS BY ORDER
    // =====================================================

    @Override
    public List<PaymentResponseDTO> getPaymentsByOrder(Long orderId) {

        return paymentRepository.findByOrderOrderId(orderId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =====================================================
    // GET PAYMENT BY TRANSACTION ID
    // =====================================================

    @Override
    public PaymentResponseDTO getPaymentByTransactionId(String transactionId) {

        Payment payment = paymentRepository
                .findByTransactionId(transactionId)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found."));

        return mapToResponse(payment);
    }
    // =====================================================
    // UPDATE PAYMENT STATUS
    // =====================================================

    @Override
    public PaymentResponseDTO updatePaymentStatus(
            Long paymentId,
            PaymentStatus paymentStatus) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found."));

        payment.setPaymentStatus(paymentStatus);

        if (paymentStatus == PaymentStatus.SUCCESS) {

            payment.setPaidAt(LocalDateTime.now());

            Order order = payment.getOrder();

            order.setPaymentStatus(
                    com.pradnyasanskar.webstore.entity.PaymentStatus.SUCCESS);

            orderRepository.save(order);

        } else if (paymentStatus == PaymentStatus.FAILED) {

            Order order = payment.getOrder();

            order.setPaymentStatus(
                    com.pradnyasanskar.webstore.entity.PaymentStatus.FAILED);

            orderRepository.save(order);

        } else if (paymentStatus == PaymentStatus.REFUNDED) {

            Order order = payment.getOrder();

            order.setPaymentStatus(
                    com.pradnyasanskar.webstore.entity.PaymentStatus.REFUNDED);

            orderRepository.save(order);
        }

        Payment updatedPayment = paymentRepository.save(payment);

        return mapToResponse(updatedPayment);
    }
    // =====================================================
    // MAP ENTITY TO DTO
    // =====================================================

    private PaymentResponseDTO mapToResponse(Payment payment) {

        PaymentResponseDTO dto = new PaymentResponseDTO();

        dto.setPaymentId(payment.getPaymentId());

        dto.setOrderId(payment.getOrder().getOrderId());

        dto.setOrderNumber(payment.getOrder().getOrderNumber());

        dto.setPaymentGateway(payment.getPaymentGateway());

        dto.setGatewayOrderId(payment.getGatewayOrderId());

        dto.setGatewayPaymentId(payment.getGatewayPaymentId());

        dto.setTransactionId(payment.getTransactionId());

        dto.setPaymentMethod(payment.getPaymentMethod());

        dto.setAmount(payment.getAmount());

        dto.setCurrency(payment.getCurrency());

        dto.setPaymentStatus(payment.getPaymentStatus());

        dto.setGatewayResponse(payment.getGatewayResponse());

        dto.setPaidAt(payment.getPaidAt());

        dto.setCreatedAt(payment.getCreatedAt());

        return dto;
    }
    //by user impl
    @Override
    public List<PaymentResponseDTO> getPaymentsByUser(Long userId) {

        return paymentRepository.findByOrder_User_UserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

}