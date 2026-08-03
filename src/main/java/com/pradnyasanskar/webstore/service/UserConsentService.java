package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.UserConsentRequestDTO;
import com.pradnyasanskar.webstore.dto.UserConsentResponseDTO;
import com.pradnyasanskar.webstore.entity.ConsentType;

import java.util.List;

public interface UserConsentService {

    // ============================================================
    // CREATE
    // ============================================================

    UserConsentResponseDTO saveConsent(
            UserConsentRequestDTO request);

    // ============================================================
    // GET BY ID
    // ============================================================

    UserConsentResponseDTO getConsentById(
            Long consentId);

    // ============================================================
    // GET ALL
    // ============================================================

    List<UserConsentResponseDTO> getAllConsents();

    // ============================================================
    // GET BY USER
    // ============================================================

    List<UserConsentResponseDTO> getUserConsents(
            Long userId);

    // ============================================================
    // GET BY CONSENT TYPE
    // ============================================================

    List<UserConsentResponseDTO> getConsentsByType(
            ConsentType consentType);

    // ============================================================
    // GET USER CONSENT BY TYPE
    // ============================================================

    List<UserConsentResponseDTO> getUserConsentsByType(
            Long userId,
            ConsentType consentType);

    // ============================================================
    // DELETE
    // ============================================================

    void deleteConsent(
            Long consentId);

}