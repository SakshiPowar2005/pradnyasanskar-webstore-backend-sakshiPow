package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.AuditLog;
import com.pradnyasanskar.webstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    // ============================================================
    // Find all audit logs of a particular user
    // ============================================================

    List<AuditLog> findByUser(User user);

    // ============================================================
    // Find logs by module
    // Example:
    // PRODUCT
    // ORDER
    // PAYMENT
    // USER
    // ============================================================

    List<AuditLog> findByModuleName(String moduleName);

}