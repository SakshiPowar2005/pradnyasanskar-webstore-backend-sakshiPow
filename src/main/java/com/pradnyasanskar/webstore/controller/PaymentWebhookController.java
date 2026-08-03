package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.PaymentWebhookRequestDTO;
import com.pradnyasanskar.webstore.dto.PaymentWebhookResponseDTO;
import com.pradnyasanskar.webstore.service.PaymentWebhookService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-webhooks")
@CrossOrigin(origins = "*")
public class PaymentWebhookController {

    private final PaymentWebhookService paymentWebhookService;

    public PaymentWebhookController(
            PaymentWebhookService paymentWebhookService) {

        this.paymentWebhookService = paymentWebhookService;
    }

    // =====================================================
    // RECEIVE PAYMENT WEBHOOK
    // =====================================================

    @PostMapping
    public ResponseEntity<PaymentWebhookResponseDTO> receiveWebhook(
            @RequestBody PaymentWebhookRequestDTO request) {

        return new ResponseEntity<>(
                paymentWebhookService.receiveWebhook(request),
                HttpStatus.CREATED);
    }

    // =====================================================
    // GET ALL WEBHOOKS
    // =====================================================

    @GetMapping
    public ResponseEntity<List<PaymentWebhookResponseDTO>> getAllWebhooks() {

        return ResponseEntity.ok(
                paymentWebhookService.getAllWebhooks());
    }

    // =====================================================
    // GET WEBHOOK BY ID
    // =====================================================

    @GetMapping("/{webhookId}")
    public ResponseEntity<PaymentWebhookResponseDTO> getWebhookById(
            @PathVariable Long webhookId) {

        return ResponseEntity.ok(
                paymentWebhookService.getWebhookById(webhookId));
    }

    // =====================================================
    // GET WEBHOOKS BY PAYMENT
    // =====================================================

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<PaymentWebhookResponseDTO>>
    getWebhooksByPayment(@PathVariable Long paymentId) {

        return ResponseEntity.ok(
                paymentWebhookService.getWebhooksByPayment(paymentId));
    }

}