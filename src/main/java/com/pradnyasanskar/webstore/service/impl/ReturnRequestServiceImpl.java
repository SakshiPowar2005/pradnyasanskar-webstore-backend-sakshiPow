package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.ReturnRequestDTO;
import com.pradnyasanskar.webstore.dto.ReturnResponseDTO;
import com.pradnyasanskar.webstore.entity.*;
import com.pradnyasanskar.webstore.repository.OrderItemRepository;
import com.pradnyasanskar.webstore.repository.ReturnRequestRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.service.ReturnRequestService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReturnRequestServiceImpl implements ReturnRequestService {

    private final ReturnRequestRepository returnRequestRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    public ReturnRequestServiceImpl(
            ReturnRequestRepository returnRequestRepository,
            OrderItemRepository orderItemRepository,
            UserRepository userRepository) {

        this.returnRequestRepository = returnRequestRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
    }

    // ============================================================
    // CREATE RETURN REQUEST
    // ============================================================

    @Override
    public ReturnResponseDTO createReturnRequest(
            ReturnRequestDTO request) {
        System.out.println("OrderItemId = " + request.getOrderItemId());
        System.out.println("CreatedBy = " + request.getCreatedBy());
        OrderItem orderItem = orderItemRepository.findById(
                        request.getOrderItemId())
                .orElseThrow(() ->
                        new RuntimeException("Order Item not found."));

        User createdBy = userRepository.findById(
                        request.getCreatedBy())
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        ReturnRequest returnRequest = new ReturnRequest();

        returnRequest.setOrderItem(orderItem);

        returnRequest.setReturnType(request.getReturnType());

        returnRequest.setReturnStatus(ReturnStatus.REQUESTED);

        returnRequest.setReason(request.getReason());

        returnRequest.setCustomerComments(
                request.getCustomerComments());

        returnRequest.setRefundAmount(
                request.getRefundAmount());

        returnRequest.setRequestedAt(
                LocalDateTime.now());

        returnRequest.setCreatedBy(createdBy);

        ReturnRequest saved =
                returnRequestRepository.save(returnRequest);

        return mapToResponse(saved);
    }

    // ============================================================
    // GET RETURN REQUEST BY ID
    // ============================================================

    @Override
    public ReturnResponseDTO getReturnRequestById(
            Long returnRequestId) {

        ReturnRequest request =
                returnRequestRepository.findById(returnRequestId)
                        .orElseThrow(() ->
                                new RuntimeException("Return Request not found."));

        return mapToResponse(request);
    }
    // ============================================================
    // GET ALL RETURN REQUESTS
    // ============================================================

    @Override
    public List<ReturnResponseDTO> getAllReturnRequests() {

        return returnRequestRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET RETURN REQUESTS BY ORDER ITEM
    // ============================================================

    @Override
    public List<ReturnResponseDTO> getReturnRequestsByOrderItem(
            Long orderItemId) {

        return returnRequestRepository
                .findByOrderItem_OrderItemId(orderItemId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET RETURN REQUESTS BY USER
    // ============================================================

    @Override
    public List<ReturnResponseDTO> getReturnRequestsByUser(
            Long userId) {

        return returnRequestRepository
                .findByCreatedBy_UserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET RETURN REQUESTS BY STATUS
    // ============================================================

    @Override
    public List<ReturnResponseDTO> getReturnRequestsByStatus(
            ReturnStatus returnStatus) {

        return returnRequestRepository
                .findByReturnStatus(returnStatus)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    // ============================================================
    // UPDATE RETURN STATUS
    // ============================================================

    @Override
    public ReturnResponseDTO updateReturnStatus(
            Long returnRequestId,
            ReturnStatus returnStatus) {

        ReturnRequest returnRequest = returnRequestRepository.findById(returnRequestId)
                .orElseThrow(() ->
                        new RuntimeException("Return request not found."));

        returnRequest.setReturnStatus(returnStatus);

        returnRequest.setProcessedAt(LocalDateTime.now());

        ReturnRequest updated =
                returnRequestRepository.save(returnRequest);

        return mapToResponse(updated);
    }

    // ============================================================
    // DELETE RETURN REQUEST
    // ============================================================

    @Override
    public void deleteReturnRequest(Long returnRequestId) {

        ReturnRequest request = returnRequestRepository.findById(returnRequestId)
                .orElseThrow(() ->
                        new RuntimeException("Return request not found."));

        returnRequestRepository.delete(request);
    }

    // ============================================================
    // ENTITY -> DTO
    // ============================================================

    private ReturnResponseDTO mapToResponse(ReturnRequest request) {

        ReturnResponseDTO response = new ReturnResponseDTO();

        response.setReturnRequestId(request.getReturnRequestId());

        response.setOrderItemId(request.getOrderItem().getOrderItemId());

        response.setOrderId(request.getOrderItem().getOrder().getOrderId());

        response.setReturnType(request.getReturnType());

        response.setReturnStatus(request.getReturnStatus());

        response.setReason(request.getReason());

        response.setCustomerComments(request.getCustomerComments());

        response.setAdminComments(request.getAdminComments());

        response.setRefundAmount(request.getRefundAmount());

        response.setRequestedAt(request.getRequestedAt());

        response.setProcessedAt(request.getProcessedAt());

        if (request.getCreatedBy() != null) {
            response.setCreatedBy(request.getCreatedBy().getUserId());
        }

        if (request.getProcessedBy() != null) {
            response.setProcessedBy(request.getProcessedBy().getUserId());
        }

        return response;
    }

}