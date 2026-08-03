package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.PaymentWebhookRequestDTO;
import com.pradnyasanskar.webstore.dto.PaymentWebhookResponseDTO;

import java.util.List;

public interface PaymentWebhookService {

    // Receive Webhook
    PaymentWebhookResponseDTO receiveWebhook(
            PaymentWebhookRequestDTO request);

    // Get All Webhooks
    List<PaymentWebhookResponseDTO> getAllWebhooks();

    // Get Webhook By ID
    PaymentWebhookResponseDTO getWebhookById(Long webhookId);

    // Get Webhooks By Payment
    List<PaymentWebhookResponseDTO> getWebhooksByPayment(Long paymentId);

}