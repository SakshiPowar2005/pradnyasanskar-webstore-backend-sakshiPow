package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.RefundRequestDTO;
import com.pradnyasanskar.webstore.dto.RefundResponseDTO;
import com.pradnyasanskar.webstore.entity.RefundStatus;
import com.pradnyasanskar.webstore.service.RefundService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/refunds")
@CrossOrigin(origins = "*")
public class RefundController {

    private final RefundService refundService;

    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    // =====================================================
    // CREATE REFUND
    // =====================================================

    @PostMapping
    public ResponseEntity<RefundResponseDTO> createRefund(
            @RequestBody RefundRequestDTO request) {

        return new ResponseEntity<>(
                refundService.createRefund(request),
                HttpStatus.CREATED);
    }

    // =====================================================
    // GET ALL REFUNDS
    // =====================================================

    @GetMapping
    public ResponseEntity<List<RefundResponseDTO>> getAllRefunds() {

        return ResponseEntity.ok(
                refundService.getAllRefunds());
    }

    // =====================================================
    // GET REFUND BY ID
    // =====================================================

    @GetMapping("/{refundId}")
    public ResponseEntity<RefundResponseDTO> getRefundById(
            @PathVariable Long refundId) {

        return ResponseEntity.ok(
                refundService.getRefundById(refundId));
    }

    // =====================================================
    // GET REFUNDS BY PAYMENT
    // =====================================================

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<RefundResponseDTO>> getRefundsByPayment(
            @PathVariable Long paymentId) {

        return ResponseEntity.ok(
                refundService.getRefundsByPayment(paymentId));
    }

    // =====================================================
    // UPDATE REFUND STATUS
    // =====================================================

    @PatchMapping("/{refundId}/status")
    public ResponseEntity<RefundResponseDTO> updateRefundStatus(

            @PathVariable Long refundId,

            @RequestParam RefundStatus refundStatus) {

        return ResponseEntity.ok(
                refundService.updateRefundStatus(
                        refundId,
                        refundStatus));
    }

}