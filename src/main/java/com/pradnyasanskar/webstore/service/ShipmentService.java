package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.ShipmentRequestDTO;
import com.pradnyasanskar.webstore.dto.ShipmentResponseDTO;
import com.pradnyasanskar.webstore.entity.ShipmentStatus;

import java.util.List;

public interface ShipmentService {

    // Create Shipment
    ShipmentResponseDTO createShipment(ShipmentRequestDTO request);

    // Get All Shipments
    List<ShipmentResponseDTO> getAllShipments();

    // Get Shipment By Id
    ShipmentResponseDTO getShipmentById(Long shipmentId);

    // Get Shipment By Order
    ShipmentResponseDTO getShipmentByOrder(Long orderId);

    // Get Shipment By Tracking Number
    ShipmentResponseDTO getShipmentByTrackingNumber(String trackingNumber);

    // Get Shipments By Status
    List<ShipmentResponseDTO> getShipmentsByStatus(ShipmentStatus status);

    // Update Shipment Status
    ShipmentResponseDTO updateShipmentStatus(
            Long shipmentId,
            ShipmentStatus shipmentStatus);

    // Delete Shipment
    void deleteShipment(Long shipmentId);


    //shipment by user
    List<ShipmentResponseDTO> getShipmentsByUser(Long userId);
}