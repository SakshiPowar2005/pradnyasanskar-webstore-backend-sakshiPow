package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.ReturnRequestDTO;
import com.pradnyasanskar.webstore.dto.ReturnResponseDTO;
import com.pradnyasanskar.webstore.entity.ReturnStatus;

import java.util.List;

public interface ReturnRequestService {

    // ============================================================
    // CREATE RETURN REQUEST
    // ============================================================

    ReturnResponseDTO createReturnRequest(
            ReturnRequestDTO request);

    // ============================================================
    // GET RETURN REQUEST BY ID
    // ============================================================

    ReturnResponseDTO getReturnRequestById(
            Long returnRequestId);

    // ============================================================
    // GET ALL RETURN REQUESTS
    // ============================================================

    List<ReturnResponseDTO> getAllReturnRequests();

    // ============================================================
    // GET RETURN REQUESTS BY ORDER ITEM
    // ============================================================

    List<ReturnResponseDTO> getReturnRequestsByOrderItem(
            Long orderItemId);

    // ============================================================
    // GET RETURN REQUESTS BY USER
    // ============================================================

    List<ReturnResponseDTO> getReturnRequestsByUser(
            Long userId);

    // ============================================================
    // GET RETURN REQUESTS BY STATUS
    // ============================================================

    List<ReturnResponseDTO> getReturnRequestsByStatus(
            ReturnStatus returnStatus);

    // ============================================================
    // UPDATE RETURN STATUS
    // ============================================================

    ReturnResponseDTO updateReturnStatus(
            Long returnRequestId,
            ReturnStatus returnStatus);

    // ============================================================
    // DELETE RETURN REQUEST
    // ============================================================

    void deleteReturnRequest(
            Long returnRequestId);

}