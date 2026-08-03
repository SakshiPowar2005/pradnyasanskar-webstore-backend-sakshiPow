package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.ReturnRequestDTO;
import com.pradnyasanskar.webstore.dto.ReturnResponseDTO;
import com.pradnyasanskar.webstore.entity.ReturnStatus;
import com.pradnyasanskar.webstore.service.ReturnRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/return-requests")
public class ReturnRequestController {

    private final ReturnRequestService returnRequestService;

    public ReturnRequestController(ReturnRequestService returnRequestService) {
        this.returnRequestService = returnRequestService;
    }

    // ============================================================
    // CREATE RETURN REQUEST
    // ============================================================

    @PostMapping
    public ResponseEntity<ReturnResponseDTO> createReturnRequest(
            @RequestBody ReturnRequestDTO request) {

        return new ResponseEntity<>(
                returnRequestService.createReturnRequest(request),
                HttpStatus.CREATED);
    }

    // ============================================================
    // GET RETURN REQUEST BY ID
    // ============================================================

    @GetMapping("/{returnRequestId}")
    public ResponseEntity<ReturnResponseDTO> getReturnRequestById(
            @PathVariable Long returnRequestId) {

        return ResponseEntity.ok(
                returnRequestService.getReturnRequestById(returnRequestId));
    }

    // ============================================================
    // GET ALL RETURN REQUESTS
    // ============================================================

    @GetMapping
    public ResponseEntity<List<ReturnResponseDTO>> getAllReturnRequests() {

        return ResponseEntity.ok(
                returnRequestService.getAllReturnRequests());
    }

    // ============================================================
    // GET RETURN REQUESTS BY ORDER ITEM
    // ============================================================

    @GetMapping("/order-item/{orderItemId}")
    public ResponseEntity<List<ReturnResponseDTO>> getReturnRequestsByOrderItem(
            @PathVariable Long orderItemId) {

        return ResponseEntity.ok(
                returnRequestService.getReturnRequestsByOrderItem(orderItemId));
    }

    // ============================================================
    // GET RETURN REQUESTS BY USER
    // ============================================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReturnResponseDTO>> getReturnRequestsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                returnRequestService.getReturnRequestsByUser(userId));
    }

    // ============================================================
    // GET RETURN REQUESTS BY STATUS
    // ============================================================

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReturnResponseDTO>> getReturnRequestsByStatus(
            @PathVariable ReturnStatus status) {

        return ResponseEntity.ok(
                returnRequestService.getReturnRequestsByStatus(status));
    }

    // ============================================================
    // UPDATE RETURN STATUS
    // ============================================================

    @PutMapping("/{returnRequestId}/status/{status}")
    public ResponseEntity<ReturnResponseDTO> updateReturnStatus(
            @PathVariable Long returnRequestId,
            @PathVariable ReturnStatus status) {

        return ResponseEntity.ok(
                returnRequestService.updateReturnStatus(
                        returnRequestId,
                        status));
    }

    // ============================================================
    // DELETE RETURN REQUEST
    // ============================================================

    @DeleteMapping("/{returnRequestId}")
    public ResponseEntity<String> deleteReturnRequest(
            @PathVariable Long returnRequestId) {

        returnRequestService.deleteReturnRequest(returnRequestId);

        return ResponseEntity.ok(
                "Return request deleted successfully.");
    }
}