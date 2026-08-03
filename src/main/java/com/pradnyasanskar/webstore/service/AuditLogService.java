package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.AuditLogRequestDTO;
import com.pradnyasanskar.webstore.dto.AuditLogResponseDTO;

import java.util.List;

public interface AuditLogService {

    AuditLogResponseDTO createAuditLog(AuditLogRequestDTO request);

    AuditLogResponseDTO getAuditLogById(Long auditLogId);

    List<AuditLogResponseDTO> getAllAuditLogs();

    List<AuditLogResponseDTO> getAuditLogsByModule(String moduleName);

    void deleteAuditLog(Long auditLogId);

}