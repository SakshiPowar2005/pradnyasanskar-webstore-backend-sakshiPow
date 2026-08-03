package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.FAQRequestDTO;
import com.pradnyasanskar.webstore.dto.FAQResponseDTO;
import com.pradnyasanskar.webstore.service.FAQService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faqs")
public class FAQController {

    private final FAQService faqService;

    public FAQController(FAQService faqService) {
        this.faqService = faqService;
    }

    // ============================================================
    // Create FAQ
    // ============================================================

    @PostMapping
    public ResponseEntity<FAQResponseDTO> createFAQ(
            @RequestBody FAQRequestDTO request) {

        return new ResponseEntity<>(
                faqService.createFAQ(request),
                HttpStatus.CREATED
        );
    }

    // ============================================================
    // Get All FAQs
    // ============================================================

    @GetMapping
    public ResponseEntity<List<FAQResponseDTO>> getAllFAQs() {

        return ResponseEntity.ok(
                faqService.getAllFAQs()
        );
    }

    // ============================================================
    // Get FAQ By ID
    // ============================================================

    @GetMapping("/{faqId}")
    public ResponseEntity<FAQResponseDTO> getFAQById(
            @PathVariable Long faqId) {

        return ResponseEntity.ok(
                faqService.getFAQById(faqId)
        );
    }

    // ============================================================
    // Get Active FAQs
    // ============================================================

    @GetMapping("/active")
    public ResponseEntity<List<FAQResponseDTO>> getActiveFAQs() {

        return ResponseEntity.ok(
                faqService.getActiveFAQs()
        );
    }

    // ============================================================
    // Search FAQs
    // ============================================================

    @GetMapping("/search")
    public ResponseEntity<List<FAQResponseDTO>> searchFAQs(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                faqService.searchFAQs(keyword)
        );
    }

    // ============================================================
    // Update FAQ
    // ============================================================

    @PutMapping("/{faqId}")
    public ResponseEntity<FAQResponseDTO> updateFAQ(
            @PathVariable Long faqId,
            @RequestBody FAQRequestDTO request) {

        return ResponseEntity.ok(
                faqService.updateFAQ(faqId, request)
        );
    }

    // ============================================================
    // Delete FAQ
    // ============================================================

    @DeleteMapping("/{faqId}")
    public ResponseEntity<String> deleteFAQ(
            @PathVariable Long faqId) {

        faqService.deleteFAQ(faqId);

        return ResponseEntity.ok(
                "FAQ deleted successfully."
        );
    }

}