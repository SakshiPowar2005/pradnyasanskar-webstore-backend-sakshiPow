package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.PaymentRequestDTO;
import com.pradnyasanskar.webstore.dto.PaymentResponseDTO;
import com.pradnyasanskar.webstore.entity.PaymentStatus;

import java.util.List;

public interface PaymentService {

    // Create Payment
    PaymentResponseDTO createPayment(PaymentRequestDTO request);

    // Get All Payments
    List<PaymentResponseDTO> getAllPayments();

    // Get Payment By ID
    PaymentResponseDTO getPaymentById(Long paymentId);

    // Get Payments By Order
    List<PaymentResponseDTO> getPaymentsByOrder(Long orderId);

    // Get Payment By Transaction ID
    PaymentResponseDTO getPaymentByTransactionId(String transactionId);

    // Update Payment Status
    PaymentResponseDTO updatePaymentStatus(
            Long paymentId,
            PaymentStatus paymentStatus);
    //by user
    List<PaymentResponseDTO> getPaymentsByUser(Long userId);



}