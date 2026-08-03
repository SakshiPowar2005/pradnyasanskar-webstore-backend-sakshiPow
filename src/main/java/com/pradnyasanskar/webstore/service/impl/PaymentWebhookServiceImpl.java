package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.PaymentWebhookRequestDTO;
import com.pradnyasanskar.webstore.dto.PaymentWebhookResponseDTO;
import com.pradnyasanskar.webstore.entity.Order;
import com.pradnyasanskar.webstore.entity.Payment;
import com.pradnyasanskar.webstore.entity.PaymentStatus;
import com.pradnyasanskar.webstore.entity.PaymentWebhook;
import com.pradnyasanskar.webstore.repository.OrderRepository;
import com.pradnyasanskar.webstore.repository.PaymentRepository;
import com.pradnyasanskar.webstore.repository.PaymentWebhookRepository;
import com.pradnyasanskar.webstore.service.PaymentWebhookService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentWebhookServiceImpl implements PaymentWebhookService {

    private final PaymentWebhookRepository paymentWebhookRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentWebhookServiceImpl(
            PaymentWebhookRepository paymentWebhookRepository,
            PaymentRepository paymentRepository,
            OrderRepository orderRepository) {

        this.paymentWebhookRepository = paymentWebhookRepository;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    // =====================================================
    // RECEIVE WEBHOOK
    // =====================================================

    @Override
    public PaymentWebhookResponseDTO receiveWebhook(
            PaymentWebhookRequestDTO request) {

        Payment payment = paymentRepository.findById(
                        request.getPaymentId())
                .orElseThrow(() ->
                        new RuntimeException("Payment not found."));

        PaymentWebhook webhook = new PaymentWebhook();

        webhook.setPayment(payment);

        webhook.setEventType(
                request.getEventType());

        webhook.setPayload(
                request.getPayload());

        webhook.setStatus(
                request.getStatus());

        webhook.setReceivedAt(
                LocalDateTime.now());

        // ==========================================
        // UPDATE PAYMENT STATUS
        // ==========================================

        if ("SUCCESS".equalsIgnoreCase(request.getStatus())) {

            payment.setPaymentStatus(PaymentStatus.SUCCESS);

            payment.setPaidAt(LocalDateTime.now());

            paymentRepository.save(payment);

            Order order = payment.getOrder();

            order.setPaymentStatus(PaymentStatus.SUCCESS);

            orderRepository.save(order);

        } else if ("FAILED".equalsIgnoreCase(request.getStatus())) {

            payment.setPaymentStatus(PaymentStatus.FAILED);

            paymentRepository.save(payment);

            Order order = payment.getOrder();

            order.setPaymentStatus(PaymentStatus.FAILED);

            orderRepository.save(order);

        }

        PaymentWebhook savedWebhook =
                paymentWebhookRepository.save(webhook);

        return mapToResponse(savedWebhook);
    }
    // =====================================================
    // GET ALL WEBHOOKS
    // =====================================================

    @Override
    public List<PaymentWebhookResponseDTO> getAllWebhooks() {

        return paymentWebhookRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =====================================================
    // GET WEBHOOK BY ID
    // =====================================================

    @Override
    public PaymentWebhookResponseDTO getWebhookById(Long webhookId) {

        PaymentWebhook webhook = paymentWebhookRepository
                .findById(webhookId)
                .orElseThrow(() ->
                        new RuntimeException("Webhook not found."));

        return mapToResponse(webhook);
    }

    // =====================================================
    // GET WEBHOOKS BY PAYMENT
    // =====================================================

    @Override
    public List<PaymentWebhookResponseDTO> getWebhooksByPayment(
            Long paymentId) {

        return paymentWebhookRepository
                .findByPaymentPaymentId(paymentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    // =====================================================
    // MAP ENTITY TO DTO
    // =====================================================

    private PaymentWebhookResponseDTO mapToResponse(
            PaymentWebhook webhook) {

        PaymentWebhookResponseDTO dto =
                new PaymentWebhookResponseDTO();

        dto.setWebhookId(
                webhook.getWebhookId());

        dto.setPaymentId(
                webhook.getPayment().getPaymentId());

        dto.setTransactionId(
                webhook.getPayment().getTransactionId());

        dto.setEventType(
                webhook.getEventType());

        dto.setPayload(
                webhook.getPayload());

        dto.setStatus(
                webhook.getStatus());

        dto.setReceivedAt(
                webhook.getReceivedAt());

        return dto;
    }

}