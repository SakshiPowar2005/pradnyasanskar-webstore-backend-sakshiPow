package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.FAQRequestDTO;
import com.pradnyasanskar.webstore.dto.FAQResponseDTO;
import com.pradnyasanskar.webstore.entity.FAQ;
import com.pradnyasanskar.webstore.repository.FAQRepository;
import com.pradnyasanskar.webstore.service.FAQService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FAQServiceImpl implements FAQService {

    private final FAQRepository faqRepository;

    public FAQServiceImpl(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    // ============================================================
    // Create FAQ
    // ============================================================

    @Override
    public FAQResponseDTO createFAQ(FAQRequestDTO request) {

        FAQ faq = new FAQ();

        faq.setQuestion(request.getQuestion());
        faq.setAnswer(request.getAnswer());
        faq.setDisplayOrder(request.getDisplayOrder());
        faq.setIsActive(request.getIsActive());

        faq.setCreatedAt(LocalDateTime.now());
        faq.setUpdatedAt(LocalDateTime.now());

        FAQ savedFAQ = faqRepository.save(faq);

        return mapToResponse(savedFAQ);
    }

    // ============================================================
    // Get FAQ By ID
    // ============================================================

    @Override
    public FAQResponseDTO getFAQById(Long faqId) {

        FAQ faq = faqRepository.findById(faqId)
                .orElseThrow(() ->
                        new RuntimeException("FAQ not found with ID : " + faqId));

        return mapToResponse(faq);
    }

    // ============================================================
    // Get All FAQs
    // ============================================================

    @Override
    public List<FAQResponseDTO> getAllFAQs() {

        return faqRepository.findAllByOrderByDisplayOrderAsc()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // Get Active FAQs
    // ============================================================

    @Override
    public List<FAQResponseDTO> getActiveFAQs() {

        return faqRepository.findByIsActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // Search FAQs
    // ============================================================

    @Override
    public List<FAQResponseDTO> searchFAQs(String keyword) {

        return faqRepository.findByQuestionContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // Update FAQ
    // ============================================================

    @Override
    public FAQResponseDTO updateFAQ(Long faqId,
                                    FAQRequestDTO request) {

        FAQ faq = faqRepository.findById(faqId)
                .orElseThrow(() ->
                        new RuntimeException("FAQ not found with ID : " + faqId));

        faq.setQuestion(request.getQuestion());
        faq.setAnswer(request.getAnswer());
        faq.setDisplayOrder(request.getDisplayOrder());
        faq.setIsActive(request.getIsActive());

        faq.setUpdatedAt(LocalDateTime.now());

        FAQ updatedFAQ = faqRepository.save(faq);

        return mapToResponse(updatedFAQ);
    }

    // ============================================================
    // Delete FAQ
    // ============================================================

    @Override
    public void deleteFAQ(Long faqId) {

        if (!faqRepository.existsById(faqId)) {
            throw new RuntimeException("FAQ not found with ID : " + faqId);
        }

        faqRepository.deleteById(faqId);
    }

    // ============================================================
    // Entity → DTO Mapper
    // ============================================================

    private FAQResponseDTO mapToResponse(FAQ faq) {

        FAQResponseDTO dto = new FAQResponseDTO();

        dto.setFaqId(faq.getFaqId());
        dto.setQuestion(faq.getQuestion());
        dto.setAnswer(faq.getAnswer());
        dto.setDisplayOrder(faq.getDisplayOrder());
        dto.setIsActive(faq.getIsActive());
        dto.setCreatedAt(faq.getCreatedAt());
        dto.setUpdatedAt(faq.getUpdatedAt());

        return dto;
    }

}