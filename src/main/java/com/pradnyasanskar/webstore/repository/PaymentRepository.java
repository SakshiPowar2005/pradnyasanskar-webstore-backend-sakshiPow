package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.Payment;
import com.pradnyasanskar.webstore.entity.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionId(String transactionId);

    Optional<Payment> findByGatewayPaymentId(String gatewayPaymentId);

    List<Payment> findByOrderOrderId(Long orderId);

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

    List<Payment> findByOrder_User_UserId(Long userId);

}