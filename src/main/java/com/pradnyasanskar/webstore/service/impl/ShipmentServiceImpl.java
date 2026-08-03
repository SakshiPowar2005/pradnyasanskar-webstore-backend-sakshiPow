package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.ShipmentRequestDTO;
import com.pradnyasanskar.webstore.dto.ShipmentResponseDTO;
import com.pradnyasanskar.webstore.entity.Order;
import com.pradnyasanskar.webstore.entity.OrderStatus;
import com.pradnyasanskar.webstore.entity.Shipment;
import com.pradnyasanskar.webstore.entity.ShipmentStatus;
import com.pradnyasanskar.webstore.repository.OrderRepository;
import com.pradnyasanskar.webstore.repository.ShipmentRepository;
import com.pradnyasanskar.webstore.service.ShipmentService;
import org.springframework.stereotype.Service;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository,
                               OrderRepository orderRepository) {

        this.shipmentRepository = shipmentRepository;
        this.orderRepository = orderRepository;
    }
    // =====================================================
// CREATE SHIPMENT
// =====================================================

    @Override
    public ShipmentResponseDTO createShipment(
            ShipmentRequestDTO request) {

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() ->
                        new RuntimeException("Order not found."));

        if (shipmentRepository.findByOrder(order).isPresent()) {
            throw new RuntimeException(
                    "Shipment already exists for this order.");
        }

        if (shipmentRepository.existsByTrackingNumber(
                request.getTrackingNumber())) {

            throw new RuntimeException(
                    "Tracking number already exists.");
        }

        Shipment shipment = new Shipment();

        shipment.setOrder(order);

        shipment.setCourierName(
                request.getCourierName());

        shipment.setTrackingNumber(
                request.getTrackingNumber());

        shipment.setExpectedDeliveryDate(
                request.getExpectedDeliveryDate());

        shipment.setRemarks(
                request.getRemarks());

        shipment.setShipmentStatus(
                ShipmentStatus.PENDING);

        Shipment savedShipment =
                shipmentRepository.save(shipment);

        return mapToResponse(savedShipment);
    }
    // =====================================================
// GET ALL SHIPMENTS
// =====================================================

    @Override
    public List<ShipmentResponseDTO> getAllShipments() {

        return shipmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    // =====================================================
// GET SHIPMENT BY ID
// =====================================================

    @Override
    public ShipmentResponseDTO getShipmentById(Long shipmentId) {

        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() ->
                        new RuntimeException("Shipment not found."));

        return mapToResponse(shipment);
    }
    // =====================================================
// GET SHIPMENT BY ORDER
// =====================================================

    @Override
    public ShipmentResponseDTO getShipmentByOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found."));

        Shipment shipment = shipmentRepository.findByOrder(order)
                .orElseThrow(() ->
                        new RuntimeException("Shipment not found."));

        return mapToResponse(shipment);
    }
    // =====================================================
// GET SHIPMENT BY TRACKING NUMBER
// =====================================================

    @Override
    public ShipmentResponseDTO getShipmentByTrackingNumber(
            String trackingNumber) {

        Shipment shipment = shipmentRepository
                .findByTrackingNumber(trackingNumber)
                .orElseThrow(() ->
                        new RuntimeException("Shipment not found."));

        return mapToResponse(shipment);
    }
    // =====================================================
// GET SHIPMENTS BY STATUS
// =====================================================

    @Override
    public List<ShipmentResponseDTO> getShipmentsByStatus(
            ShipmentStatus status) {

        return shipmentRepository.findByShipmentStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    // =====================================================
// UPDATE SHIPMENT STATUS
// =====================================================

    @Override
    public ShipmentResponseDTO updateShipmentStatus(
            Long shipmentId,
            ShipmentStatus shipmentStatus) {

        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() ->
                        new RuntimeException("Shipment not found."));

        shipment.setShipmentStatus(shipmentStatus);

        Order order = shipment.getOrder();

        switch (shipmentStatus) {

            case PACKED:
                order.setOrderStatus(OrderStatus.PACKED);
                break;

            case SHIPPED:
                order.setOrderStatus(OrderStatus.SHIPPED);
                shipment.setShippedAt(LocalDateTime.now());
                break;

            case OUT_FOR_DELIVERY:
                order.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
                break;

            case DELIVERED:
                order.setOrderStatus(OrderStatus.DELIVERED);
                shipment.setDeliveredAt(LocalDateTime.now());
                break;

            case RETURNED:
                order.setOrderStatus(OrderStatus.RETURNED);
                break;

            default:
                order.setOrderStatus(OrderStatus.PENDING);
                break;
        }

        orderRepository.save(order);

        Shipment updatedShipment = shipmentRepository.save(shipment);

        return mapToResponse(updatedShipment);
    }
    // =====================================================
// DELETE SHIPMENT
// =====================================================

    @Override
    public void deleteShipment(Long shipmentId) {

        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() ->
                        new RuntimeException("Shipment not found."));

        shipmentRepository.delete(shipment);
    }
    // =====================================================
// MAP ENTITY TO DTO
// =====================================================

    private ShipmentResponseDTO mapToResponse(Shipment shipment) {

        ShipmentResponseDTO response = new ShipmentResponseDTO();

        response.setShipmentId(shipment.getShipmentId());

        response.setOrderId(shipment.getOrder().getOrderId());

        response.setOrderNumber(
                shipment.getOrder().getOrderNumber());

        response.setCourierName(
                shipment.getCourierName());

        response.setTrackingNumber(
                shipment.getTrackingNumber());

        response.setShipmentStatus(
                shipment.getShipmentStatus());

        response.setExpectedDeliveryDate(
                shipment.getExpectedDeliveryDate());

        response.setShippedAt(
                shipment.getShippedAt());

        response.setDeliveredAt(
                shipment.getDeliveredAt());

        response.setRemarks(
                shipment.getRemarks());

        response.setCreatedAt(
                shipment.getCreatedAt());

        return response;
    }
    //shipment by user
    @Override
    public List<ShipmentResponseDTO> getShipmentsByUser(Long userId) {

        return shipmentRepository.findByOrder_User_UserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}