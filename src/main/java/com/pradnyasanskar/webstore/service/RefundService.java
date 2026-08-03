package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.RefundRequestDTO;
import com.pradnyasanskar.webstore.dto.RefundResponseDTO;
import com.pradnyasanskar.webstore.entity.RefundStatus;

import java.util.List;

public interface RefundService {

    // Create Refund
    RefundResponseDTO createRefund(RefundRequestDTO request);

    // Get All Refunds
    List<RefundResponseDTO> getAllRefunds();

    // Get Refund By ID
    RefundResponseDTO getRefundById(Long refundId);

    // Get Refunds By Payment
    List<RefundResponseDTO> getRefundsByPayment(Long paymentId);

    // Update Refund Status
    RefundResponseDTO updateRefundStatus(
            Long refundId,
            RefundStatus refundStatus);
    // refund by user
    List<RefundResponseDTO> getRefundsByUser(Long userId);
}