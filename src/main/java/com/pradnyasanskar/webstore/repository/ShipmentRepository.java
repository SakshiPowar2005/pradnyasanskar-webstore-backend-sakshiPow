package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.Order;
import com.pradnyasanskar.webstore.entity.Shipment;
import com.pradnyasanskar.webstore.entity.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    Optional<Shipment> findByOrder(Order order);

    Optional<Shipment> findByTrackingNumber(String trackingNumber);

    List<Shipment> findByShipmentStatus(ShipmentStatus shipmentStatus);

    boolean existsByTrackingNumber(String trackingNumber);

    List<Shipment> findByOrder_User_UserId(Long userId);
}