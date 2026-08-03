package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.PaymentWebhook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentWebhookRepository
        extends JpaRepository<PaymentWebhook, Long> {

    List<PaymentWebhook> findByPaymentPaymentId(Long paymentId);

    List<PaymentWebhook> findByEventType(String eventType);

    List<PaymentWebhook> findByStatus(String status);

}