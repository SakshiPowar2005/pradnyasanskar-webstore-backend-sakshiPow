package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.B2BEnquiryRequestDTO;
import com.pradnyasanskar.webstore.dto.B2BEnquiryResponseDTO;
import com.pradnyasanskar.webstore.entity.EnquiryStatus;
import com.pradnyasanskar.webstore.service.B2BEnquiryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/b2b-enquiries")
public class B2BEnquiryController {

    private final B2BEnquiryService b2BEnquiryService;

    public B2BEnquiryController(
            B2BEnquiryService b2BEnquiryService) {

        this.b2BEnquiryService = b2BEnquiryService;
    }

    // ============================================================
    // CREATE ENQUIRY
    // ============================================================

    @PostMapping
    public ResponseEntity<B2BEnquiryResponseDTO> createEnquiry(
            @RequestBody B2BEnquiryRequestDTO request) {

        return new ResponseEntity<>(
                b2BEnquiryService.createEnquiry(request),
                HttpStatus.CREATED);
    }

    // ============================================================
    // GET ENQUIRY BY ID
    // ============================================================

    @GetMapping("/{enquiryId}")
    public ResponseEntity<B2BEnquiryResponseDTO> getEnquiryById(
            @PathVariable Long enquiryId) {

        return ResponseEntity.ok(
                b2BEnquiryService.getEnquiryById(enquiryId));
    }

    // ============================================================
    // GET ALL ENQUIRIES
    // ============================================================

    @GetMapping
    public ResponseEntity<List<B2BEnquiryResponseDTO>> getAllEnquiries() {

        return ResponseEntity.ok(
                b2BEnquiryService.getAllEnquiries());
    }

    // ============================================================
    // GET ENQUIRIES BY STATUS
    // ============================================================

    @GetMapping("/status/{status}")
    public ResponseEntity<List<B2BEnquiryResponseDTO>>
    getEnquiriesByStatus(
            @PathVariable EnquiryStatus status) {

        return ResponseEntity.ok(
                b2BEnquiryService.getEnquiriesByStatus(status));
    }

    // ============================================================
    // GET ENQUIRIES BY EMAIL
    // ============================================================

    @GetMapping("/email/{email}")
    public ResponseEntity<List<B2BEnquiryResponseDTO>>
    getEnquiriesByEmail(
            @PathVariable String email) {

        return ResponseEntity.ok(
                b2BEnquiryService.getEnquiriesByEmail(email));
    }

    // ============================================================
    // UPDATE STATUS
    // ============================================================

    @PutMapping("/{enquiryId}/status")
    public ResponseEntity<B2BEnquiryResponseDTO> updateStatus(

            @PathVariable Long enquiryId,

            @RequestParam EnquiryStatus status) {

        return ResponseEntity.ok(
                b2BEnquiryService.updateStatus(
                        enquiryId,
                        status));
    }

    // ============================================================
    // DELETE ENQUIRY
    // ============================================================

    @DeleteMapping("/{enquiryId}")
    public ResponseEntity<String> deleteEnquiry(
            @PathVariable Long enquiryId) {

        b2BEnquiryService.deleteEnquiry(enquiryId);

        return ResponseEntity.ok(
                "B2B Enquiry deleted successfully.");
    }

}