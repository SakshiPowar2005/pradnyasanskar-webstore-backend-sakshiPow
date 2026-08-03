package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.UserConsentRequestDTO;
import com.pradnyasanskar.webstore.dto.UserConsentResponseDTO;
import com.pradnyasanskar.webstore.entity.ConsentType;
import com.pradnyasanskar.webstore.entity.User;
import com.pradnyasanskar.webstore.entity.UserConsent;
import com.pradnyasanskar.webstore.repository.UserConsentRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.service.UserConsentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserConsentServiceImpl implements UserConsentService {

    private final UserConsentRepository userConsentRepository;
    private final UserRepository userRepository;

    public UserConsentServiceImpl(
            UserConsentRepository userConsentRepository,
            UserRepository userRepository) {

        this.userConsentRepository = userConsentRepository;
        this.userRepository = userRepository;
    }

    // ============================================================
    // SAVE CONSENT
    // ============================================================

    @Override
    public UserConsentResponseDTO saveConsent(
            UserConsentRequestDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found."));

        UserConsent consent = new UserConsent();

        consent.setUser(user);

        consent.setConsentType(request.getConsentType());

        consent.setConsentVersion(request.getConsentVersion());

        consent.setAccepted(request.getAccepted());

        consent.setIpAddress(request.getIpAddress());

        consent.setUserAgent(request.getUserAgent());

        UserConsent savedConsent =
                userConsentRepository.save(consent);

        return mapToResponse(savedConsent);
    }
    // ============================================================
    // GET CONSENT BY ID
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public UserConsentResponseDTO getConsentById(Long consentId) {

        UserConsent consent = userConsentRepository.findById(consentId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User consent not found."));

        return mapToResponse(consent);
    }

    // ============================================================
    // GET ALL CONSENTS
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<UserConsentResponseDTO> getAllConsents() {

        return userConsentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ============================================================
    // GET USER CONSENTS
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<UserConsentResponseDTO> getUserConsents(Long userId) {

        return userConsentRepository.findByUser_UserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ============================================================
    // GET CONSENTS BY TYPE
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<UserConsentResponseDTO> getConsentsByType(
            ConsentType consentType) {

        return userConsentRepository.findByConsentType(consentType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ============================================================
    // GET USER CONSENTS BY TYPE
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<UserConsentResponseDTO> getUserConsentsByType(
            Long userId,
            ConsentType consentType) {

        return userConsentRepository
                .findByUser_UserIdAndConsentType(userId, consentType)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ============================================================
    // DELETE CONSENT
    // ============================================================

    @Override
    public void deleteConsent(Long consentId) {

        UserConsent consent = userConsentRepository.findById(consentId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User consent not found."));

        userConsentRepository.delete(consent);
    }

    // ============================================================
    // ENTITY -> DTO
    // ============================================================

    private UserConsentResponseDTO mapToResponse(UserConsent consent) {

        UserConsentResponseDTO response = new UserConsentResponseDTO();

        response.setConsentId(consent.getConsentId());

        response.setUserId(consent.getUser().getUserId());

        response.setConsentType(consent.getConsentType());

        response.setConsentVersion(consent.getConsentVersion());

        response.setAccepted(consent.getAccepted());

        response.setAcceptedAt(consent.getAcceptedAt());

        response.setIpAddress(consent.getIpAddress());

        response.setUserAgent(consent.getUserAgent());

        response.setCreatedAt(consent.getCreatedAt());

        return response;
    }

}