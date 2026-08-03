package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.FAQRequestDTO;
import com.pradnyasanskar.webstore.dto.FAQResponseDTO;

import java.util.List;

public interface FAQService {

    // ============================================================
    // Create FAQ
    // ============================================================

    FAQResponseDTO createFAQ(FAQRequestDTO request);

    // ============================================================
    // Get FAQ By ID
    // ============================================================

    FAQResponseDTO getFAQById(Long faqId);

    // ============================================================
    // Get All FAQs
    // ============================================================

    List<FAQResponseDTO> getAllFAQs();

    // ============================================================
    // Get Active FAQs
    // ============================================================

    List<FAQResponseDTO> getActiveFAQs();

    // ============================================================
    // Search FAQs
    // ============================================================

    List<FAQResponseDTO> searchFAQs(String keyword);

    // ============================================================
    // Update FAQ
    // ============================================================

    FAQResponseDTO updateFAQ(
            Long faqId,
            FAQRequestDTO request);

    // ============================================================
    // Delete FAQ
    // ============================================================

    void deleteFAQ(Long faqId);

}