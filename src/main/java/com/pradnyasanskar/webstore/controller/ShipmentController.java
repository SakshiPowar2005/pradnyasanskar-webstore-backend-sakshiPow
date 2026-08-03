package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.ShipmentRequestDTO;
import com.pradnyasanskar.webstore.dto.ShipmentResponseDTO;
import com.pradnyasanskar.webstore.dto.UpdateShipmentStatusDTO;
import com.pradnyasanskar.webstore.entity.ShipmentStatus;
import com.pradnyasanskar.webstore.service.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    // =====================================================
    // CREATE SHIPMENT
    // =====================================================

    @PostMapping
    public ResponseEntity<ShipmentResponseDTO> createShipment(
            @RequestBody ShipmentRequestDTO request) {

        return new ResponseEntity<>(
                shipmentService.createShipment(request),
                HttpStatus.CREATED);
    }

    // =====================================================
    // GET ALL SHIPMENTS
    // =====================================================

    @GetMapping
    public ResponseEntity<List<ShipmentResponseDTO>> getAllShipments() {

        return ResponseEntity.ok(
                shipmentService.getAllShipments());
    }

    // =====================================================
    // GET SHIPMENT BY ID
    // =====================================================

    @GetMapping("/{shipmentId}")
    public ResponseEntity<ShipmentResponseDTO> getShipmentById(
            @PathVariable Long shipmentId) {

        return ResponseEntity.ok(
                shipmentService.getShipmentById(shipmentId));
    }

    // =====================================================
    // GET SHIPMENT BY ORDER
    // =====================================================

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ShipmentResponseDTO> getShipmentByOrder(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                shipmentService.getShipmentByOrder(orderId));
    }

    // =====================================================
    // GET SHIPMENT BY TRACKING NUMBER
    // =====================================================

    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<ShipmentResponseDTO> getShipmentByTrackingNumber(
            @PathVariable String trackingNumber) {

        return ResponseEntity.ok(
                shipmentService.getShipmentByTrackingNumber(trackingNumber));
    }

    // =====================================================
    // GET SHIPMENTS BY STATUS
    // =====================================================

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ShipmentResponseDTO>> getShipmentsByStatus(
            @PathVariable ShipmentStatus status) {

        return ResponseEntity.ok(
                shipmentService.getShipmentsByStatus(status));
    }

    // =====================================================
    // UPDATE SHIPMENT STATUS
    // =====================================================

    @PutMapping("/{shipmentId}/status")
    public ResponseEntity<ShipmentResponseDTO> updateShipmentStatus(
            @PathVariable Long shipmentId,
            @RequestBody UpdateShipmentStatusDTO request) {

        return ResponseEntity.ok(
                shipmentService.updateShipmentStatus(
                        shipmentId,
                        request.getShipmentStatus()));
    }

    // =====================================================
    // DELETE SHIPMENT
    // =====================================================

    @DeleteMapping("/{shipmentId}")
    public ResponseEntity<String> deleteShipment(
            @PathVariable Long shipmentId) {

        shipmentService.deleteShipment(shipmentId);

        return ResponseEntity.ok(
                "Shipment deleted successfully.");
    }
}