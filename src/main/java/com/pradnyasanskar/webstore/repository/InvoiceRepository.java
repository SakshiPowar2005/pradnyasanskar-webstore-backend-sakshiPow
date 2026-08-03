package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.Invoice;
import com.pradnyasanskar.webstore.entity.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    Optional<Invoice> findByOrderOrderId(Long orderId);

    List<Invoice> findByInvoiceStatus(InvoiceStatus invoiceStatus);

    boolean existsByOrderOrderId(Long orderId);

    List<Invoice> findByOrder_User_UserId(Long userId);

}