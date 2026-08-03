package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.PaymentRequestDTO;
import com.pradnyasanskar.webstore.dto.PaymentResponseDTO;
import com.pradnyasanskar.webstore.entity.PaymentStatus;
import com.pradnyasanskar.webstore.service.PaymentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // =====================================================
    // CREATE PAYMENT
    // =====================================================

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @RequestBody PaymentRequestDTO request) {

        return new ResponseEntity<>(
                paymentService.createPayment(request),
                HttpStatus.CREATED);
    }

    // =====================================================
    // GET ALL PAYMENTS
    // =====================================================

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {

        return ResponseEntity.ok(
                paymentService.getAllPayments());
    }

    // =====================================================
    // GET PAYMENT BY ID
    // =====================================================

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(
            @PathVariable Long paymentId) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(paymentId));
    }

    // =====================================================
    // GET PAYMENTS BY ORDER
    // =====================================================

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentResponseDTO>> getPaymentsByOrder(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                paymentService.getPaymentsByOrder(orderId));
    }

    // =====================================================
    // GET PAYMENT BY TRANSACTION ID
    // =====================================================

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByTransactionId(
            @PathVariable String transactionId) {

        return ResponseEntity.ok(
                paymentService.getPaymentByTransactionId(transactionId));
    }

    // =====================================================
    // UPDATE PAYMENT STATUS
    // =====================================================

    @PatchMapping("/{paymentId}/status")
    public ResponseEntity<PaymentResponseDTO> updatePaymentStatus(

            @PathVariable Long paymentId,

            @RequestParam PaymentStatus paymentStatus) {

        return ResponseEntity.ok(
                paymentService.updatePaymentStatus(
                        paymentId,
                        paymentStatus));
    }

}