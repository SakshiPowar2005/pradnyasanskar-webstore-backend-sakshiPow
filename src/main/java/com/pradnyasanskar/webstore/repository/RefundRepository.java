package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.Refund;
import com.pradnyasanskar.webstore.entity.RefundStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund, Long> {

    List<Refund> findByPaymentPaymentId(Long paymentId);

    List<Refund> findByRefundStatus(RefundStatus refundStatus);

    Optional<Refund> findByGatewayRefundId(String gatewayRefundId);

    List<Refund> findByPayment_Order_User_UserId(Long userId);

}