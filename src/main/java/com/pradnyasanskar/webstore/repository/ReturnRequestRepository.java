package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.ReturnRequest;
import com.pradnyasanskar.webstore.entity.ReturnStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {

    // Get all return requests of an order item
    List<ReturnRequest> findByOrderItem_OrderItemId(Long orderItemId);

    // Get return requests by status
    List<ReturnRequest> findByReturnStatus(ReturnStatus returnStatus);

    // Get return requests created by user
    List<ReturnRequest> findByCreatedBy_UserId(Long userId);

}