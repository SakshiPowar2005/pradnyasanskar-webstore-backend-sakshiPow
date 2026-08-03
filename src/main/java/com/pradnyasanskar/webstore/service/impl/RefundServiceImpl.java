package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.RefundRequestDTO;
import com.pradnyasanskar.webstore.dto.RefundResponseDTO;
import com.pradnyasanskar.webstore.entity.Order;
import com.pradnyasanskar.webstore.entity.Payment;
import com.pradnyasanskar.webstore.entity.Refund;
import com.pradnyasanskar.webstore.entity.RefundStatus;
import com.pradnyasanskar.webstore.repository.PaymentRepository;
import com.pradnyasanskar.webstore.repository.RefundRepository;
import com.pradnyasanskar.webstore.service.RefundService;
import com.pradnyasanskar.webstore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RefundServiceImpl implements RefundService {

    private final RefundRepository refundRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public RefundServiceImpl(
            RefundRepository refundRepository,
            PaymentRepository paymentRepository,
            OrderRepository orderRepository) {

        this.refundRepository = refundRepository;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    // =====================================================
    // CREATE REFUND
    // =====================================================

    @Override
    public RefundResponseDTO createRefund(RefundRequestDTO request) {

        Payment payment = paymentRepository.findById(request.getPaymentId())
                .orElseThrow(() ->
                        new RuntimeException("Payment not found."));

        if (payment.getPaymentStatus()
                != com.pradnyasanskar.webstore.entity.PaymentStatus.SUCCESS) {

            throw new RuntimeException(
                    "Refund can only be created for successful payments.");
        }

        Refund refund = new Refund();

        refund.setPayment(payment);

        refund.setRefundAmount(
                request.getRefundAmount());

        refund.setRefundReason(
                request.getRefundReason());

        refund.setRefundStatus(
                RefundStatus.PENDING);

        refund.setGatewayRefundId(
                "REF-" +
                        UUID.randomUUID()
                                .toString()
                                .substring(0, 10)
                                .toUpperCase());

        Refund savedRefund =
                refundRepository.save(refund);

        return mapToResponse(savedRefund);
    }
    // =====================================================
    // GET ALL REFUNDS
    // =====================================================

    @Override
    public List<RefundResponseDTO> getAllRefunds() {

        return refundRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =====================================================
    // GET REFUND BY ID
    // =====================================================

    @Override
    public RefundResponseDTO getRefundById(Long refundId) {

        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() ->
                        new RuntimeException("Refund not found."));

        return mapToResponse(refund);
    }

    // =====================================================
    // GET REFUNDS BY PAYMENT
    // =====================================================

    @Override
    public List<RefundResponseDTO> getRefundsByPayment(Long paymentId) {

        return refundRepository.findByPaymentPaymentId(paymentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    // =====================================================
    // UPDATE REFUND STATUS
    // =====================================================

    @Override
    public RefundResponseDTO updateRefundStatus(
            Long refundId,
            RefundStatus refundStatus) {

        Refund refund = refundRepository.findById(refundId)
                .orElseThrow(() ->
                        new RuntimeException("Refund not found."));

        refund.setRefundStatus(refundStatus);

        if (refundStatus == RefundStatus.SUCCESS) {

            refund.setRefundedAt(java.time.LocalDateTime.now());

            // ======================================
            // UPDATE PAYMENT STATUS
            // ======================================

            Payment payment = refund.getPayment();

            payment.setPaymentStatus(
                    com.pradnyasanskar.webstore.entity.PaymentStatus.REFUNDED);

            paymentRepository.save(payment);

            // ======================================
            // UPDATE ORDER PAYMENT STATUS
            // ======================================

            Order order = payment.getOrder();

            order.setPaymentStatus(
                    com.pradnyasanskar.webstore.entity.PaymentStatus.REFUNDED);

            orderRepository.save(order);

        }

        Refund updatedRefund = refundRepository.save(refund);

        return mapToResponse(updatedRefund);
    }
    // =====================================================
    // MAP ENTITY TO DTO
    // =====================================================

    private RefundResponseDTO mapToResponse(Refund refund) {

        RefundResponseDTO dto = new RefundResponseDTO();

        dto.setRefundId(refund.getRefundId());

        dto.setPaymentId(
                refund.getPayment().getPaymentId());

        dto.setTransactionId(
                refund.getPayment().getTransactionId());

        dto.setRefundAmount(
                refund.getRefundAmount());

        dto.setRefundReason(
                refund.getRefundReason());

        dto.setGatewayRefundId(
                refund.getGatewayRefundId());

        dto.setRefundStatus(
                refund.getRefundStatus());

        dto.setRefundedAt(
                refund.getRefundedAt());

        dto.setCreatedAt(
                refund.getCreatedAt());

        return dto;
    }
    //refund by user
    @Override
    public List<RefundResponseDTO> getRefundsByUser(Long userId) {

        return refundRepository.findByPayment_Order_User_UserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

}