package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.AuditLogRequestDTO;
import com.pradnyasanskar.webstore.dto.AuditLogResponseDTO;
import com.pradnyasanskar.webstore.service.AuditLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(
            AuditLogService auditLogService) {

        this.auditLogService = auditLogService;
    }

    // ============================================================
    // CREATE AUDIT LOG
    // ============================================================

    @PostMapping
    public ResponseEntity<AuditLogResponseDTO> createAuditLog(
            @RequestBody AuditLogRequestDTO request) {

        return new ResponseEntity<>(
                auditLogService.createAuditLog(request),
                HttpStatus.CREATED);
    }

    // ============================================================
    // GET AUDIT LOG BY ID
    // ============================================================

    @GetMapping("/{auditLogId}")
    public ResponseEntity<AuditLogResponseDTO> getAuditLogById(
            @PathVariable Long auditLogId) {

        return ResponseEntity.ok(
                auditLogService.getAuditLogById(auditLogId));
    }

    // ============================================================
    // GET ALL AUDIT LOGS
    // ============================================================

    @GetMapping
    public ResponseEntity<List<AuditLogResponseDTO>> getAllAuditLogs() {

        return ResponseEntity.ok(
                auditLogService.getAllAuditLogs());
    }

    // ============================================================
    // DELETE AUDIT LOG
    // ============================================================

    @DeleteMapping("/{auditLogId}")
    public ResponseEntity<String> deleteAuditLog(
            @PathVariable Long auditLogId) {

        auditLogService.deleteAuditLog(auditLogId);

        return ResponseEntity.ok(
                "Audit Log deleted successfully.");
    }
    // ============================================================
// GET AUDIT LOGS BY MODULE
// ============================================================

    @GetMapping("/module/{moduleName}")
    public ResponseEntity<List<AuditLogResponseDTO>> getAuditLogsByModule(
            @PathVariable String moduleName) {

        return ResponseEntity.ok(
                auditLogService.getAuditLogsByModule(moduleName));
    }



}