package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.AuditLogRequestDTO;
import com.pradnyasanskar.webstore.dto.AuditLogResponseDTO;
import com.pradnyasanskar.webstore.entity.AuditLog;
import com.pradnyasanskar.webstore.entity.User;
import com.pradnyasanskar.webstore.repository.AuditLogRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.service.AuditLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    private final UserRepository userRepository;

    public AuditLogServiceImpl(
            AuditLogRepository auditLogRepository,
            UserRepository userRepository) {

        this.auditLogRepository = auditLogRepository;
        this.userRepository = userRepository;
    }

    // ============================================================
    // CREATE AUDIT LOG
    // ============================================================

    @Override
    public AuditLogResponseDTO createAuditLog(
            AuditLogRequestDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        AuditLog auditLog = new AuditLog();

        auditLog.setUser(user);

        auditLog.setModuleName(request.getModuleName());

        auditLog.setAction(request.getAction());

        auditLog.setTableName(request.getTableName());

        auditLog.setRecordId(request.getRecordId());

        auditLog.setOldData(request.getOldData());

        auditLog.setNewData(request.getNewData());

        auditLog.setIpAddress(request.getIpAddress());

        auditLog.setUserAgent(request.getUserAgent());

        auditLog.setCreatedAt(LocalDateTime.now());

        AuditLog savedAuditLog =
                auditLogRepository.save(auditLog);

        return mapToResponse(savedAuditLog);
    }

    // ============================================================
    // GET AUDIT LOG BY ID
    // ============================================================

    @Override
    public AuditLogResponseDTO getAuditLogById(
            Long auditLogId) {

        AuditLog auditLog =
                auditLogRepository.findById(auditLogId)
                        .orElseThrow(() ->
                                new RuntimeException("Audit Log not found."));

        return mapToResponse(auditLog);
    }

    // ============================================================
    // GET ALL AUDIT LOGS
    // ============================================================

    @Override
    public List<AuditLogResponseDTO> getAllAuditLogs() {

        return auditLogRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET AUDIT LOGS BY MODULE
    // ============================================================

    @Override
    public List<AuditLogResponseDTO> getAuditLogsByModule(
            String moduleName) {

        return auditLogRepository.findByModuleName(moduleName)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // DELETE AUDIT LOG
    // ============================================================

    @Override
    public void deleteAuditLog(
            Long auditLogId) {

        AuditLog auditLog =
                auditLogRepository.findById(auditLogId)
                        .orElseThrow(() ->
                                new RuntimeException("Audit Log not found."));

        auditLogRepository.delete(auditLog);
    }

    // ============================================================
    // ENTITY TO DTO
    // ============================================================

    private AuditLogResponseDTO mapToResponse(
            AuditLog auditLog) {

        AuditLogResponseDTO dto =
                new AuditLogResponseDTO();

        dto.setAuditLogId(auditLog.getAuditLogId());

        dto.setUserId(auditLog.getUser().getUserId());

        dto.setUserName(
                auditLog.getUser().getFirstName()
                        + " "
                        + auditLog.getUser().getLastName());

        dto.setModuleName(auditLog.getModuleName());

        dto.setAction(auditLog.getAction());

        dto.setTableName(auditLog.getTableName());

        dto.setRecordId(auditLog.getRecordId());

        dto.setOldData(auditLog.getOldData());

        dto.setNewData(auditLog.getNewData());

        dto.setIpAddress(auditLog.getIpAddress());

        dto.setUserAgent(auditLog.getUserAgent());

        dto.setCreatedAt(auditLog.getCreatedAt());

        return dto;
    }

}