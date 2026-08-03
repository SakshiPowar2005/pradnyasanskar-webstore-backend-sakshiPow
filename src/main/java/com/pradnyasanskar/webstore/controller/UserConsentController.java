package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.UserConsentRequestDTO;
import com.pradnyasanskar.webstore.dto.UserConsentResponseDTO;
import com.pradnyasanskar.webstore.entity.ConsentType;
import com.pradnyasanskar.webstore.service.UserConsentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-consents")
public class UserConsentController {

    private final UserConsentService userConsentService;

    public UserConsentController(UserConsentService userConsentService) {
        this.userConsentService = userConsentService;
    }

    // ============================================================
    // CREATE CONSENT
    // ============================================================

    @PostMapping
    public ResponseEntity<UserConsentResponseDTO> saveConsent(
            @RequestBody UserConsentRequestDTO request) {

        return new ResponseEntity<>(
                userConsentService.saveConsent(request),
                HttpStatus.CREATED
        );
    }

    // ============================================================
    // GET CONSENT BY ID
    // ============================================================

    @GetMapping("/{consentId}")
    public ResponseEntity<UserConsentResponseDTO> getConsentById(
            @PathVariable Long consentId) {

        return ResponseEntity.ok(
                userConsentService.getConsentById(consentId)
        );
    }

    // ============================================================
    // GET ALL CONSENTS
    // ============================================================

    @GetMapping
    public ResponseEntity<List<UserConsentResponseDTO>> getAllConsents() {

        return ResponseEntity.ok(
                userConsentService.getAllConsents()
        );
    }

    // ============================================================
    // GET ALL CONSENTS OF USER
    // ============================================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserConsentResponseDTO>> getUserConsents(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                userConsentService.getUserConsents(userId)
        );
    }

    // ============================================================
    // GET CONSENTS BY TYPE
    // ============================================================

    @GetMapping("/type/{consentType}")
    public ResponseEntity<List<UserConsentResponseDTO>> getConsentsByType(
            @PathVariable ConsentType consentType) {

        return ResponseEntity.ok(
                userConsentService.getConsentsByType(consentType)
        );
    }

    // ============================================================
    // GET USER CONSENTS BY TYPE
    // ============================================================

    @GetMapping("/user/{userId}/type/{consentType}")
    public ResponseEntity<List<UserConsentResponseDTO>> getUserConsentsByType(
            @PathVariable Long userId,
            @PathVariable ConsentType consentType) {

        return ResponseEntity.ok(
                userConsentService.getUserConsentsByType(
                        userId,
                        consentType
                )
        );
    }

    // ============================================================
    // DELETE CONSENT
    // ============================================================

    @DeleteMapping("/{consentId}")
    public ResponseEntity<String> deleteConsent(
            @PathVariable Long consentId) {

        userConsentService.deleteConsent(consentId);

        return ResponseEntity.ok("User consent deleted successfully.");
    }
}