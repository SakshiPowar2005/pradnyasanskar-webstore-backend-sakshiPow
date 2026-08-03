package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.*;

import java.util.List;

public interface DashboardService {

    // ===============================
    // Complete Dashboard
    // ===============================
    DashboardResponseDTO getDashboard(Long userId);

    // ===============================
    // Customer Profile
    // ===============================
    UserResponseDTO getProfile(Long userId);

    // ===============================
    // Orders
    // ===============================
    List<OrderResponseDTO> getOrders(Long userId);

    // ===============================
    // Shopping Cart
    // ===============================
    CartResponseDTO getCart(Long userId);

    // ===============================
    // Payments
    // ===============================
    List<PaymentResponseDTO> getPayments(Long userId);

    // ===============================
    // Refunds
    // ===============================
    List<RefundResponseDTO> getRefunds(Long userId);

    // ===============================
    // Shipments
    // ===============================
    List<ShipmentResponseDTO> getShipments(Long userId);

    // ===============================
    // Invoices
    // ===============================
    List<InvoiceResponseDTO> getInvoices(Long userId);

    // ===============================
    // Addresses
    // ===============================
    List<AddressResponseDTO> getAddresses(Long userId);
}