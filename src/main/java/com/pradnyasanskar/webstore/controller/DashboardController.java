package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.*;
import com.pradnyasanskar.webstore.service.DashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // ============================================================
    // Complete Dashboard
    // ============================================================

    @GetMapping("/{userId}")
    public ResponseEntity<DashboardResponseDTO> getDashboard(
            @PathVariable Long userId) {

        return new ResponseEntity<>(
                dashboardService.getDashboard(userId),
                HttpStatus.OK
        );
    }

    // ============================================================
    // Profile
    // ============================================================

    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserResponseDTO> getProfile(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getProfile(userId)
        );
    }

    // ============================================================
    // Orders
    // ============================================================

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<OrderResponseDTO>> getOrders(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getOrders(userId)
        );
    }

    // ============================================================
    // Cart
    // ============================================================

    @GetMapping("/{userId}/cart")
    public ResponseEntity<CartResponseDTO> getCart(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getCart(userId)
        );
    }

    // ============================================================
    // Payments
    // ============================================================

    @GetMapping("/{userId}/payments")
    public ResponseEntity<List<PaymentResponseDTO>> getPayments(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getPayments(userId)
        );
    }

    // ============================================================
    // Refunds
    // ============================================================

    @GetMapping("/{userId}/refunds")
    public ResponseEntity<List<RefundResponseDTO>> getRefunds(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getRefunds(userId)
        );
    }

    // ============================================================
    // Shipments
    // ============================================================

    @GetMapping("/{userId}/shipments")
    public ResponseEntity<List<ShipmentResponseDTO>> getShipments(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getShipments(userId)
        );
    }

    // ============================================================
    // Invoices
    // ============================================================

    @GetMapping("/{userId}/invoices")
    public ResponseEntity<List<InvoiceResponseDTO>> getInvoices(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getInvoices(userId)
        );
    }

    // ============================================================
    // Addresses
    // ============================================================

    @GetMapping("/{userId}/addresses")
    public ResponseEntity<List<AddressResponseDTO>> getAddresses(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                dashboardService.getAddresses(userId)
        );
    }
}