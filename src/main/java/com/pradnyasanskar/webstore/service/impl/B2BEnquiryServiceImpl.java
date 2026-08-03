package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.B2BEnquiryRequestDTO;
import com.pradnyasanskar.webstore.dto.B2BEnquiryResponseDTO;
import com.pradnyasanskar.webstore.entity.B2BEnquiry;
import com.pradnyasanskar.webstore.entity.EnquiryStatus;
import com.pradnyasanskar.webstore.repository.B2BEnquiryRepository;
import com.pradnyasanskar.webstore.service.B2BEnquiryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class B2BEnquiryServiceImpl implements B2BEnquiryService {

    private final B2BEnquiryRepository enquiryRepository;

    public B2BEnquiryServiceImpl(B2BEnquiryRepository enquiryRepository) {
        this.enquiryRepository = enquiryRepository;
    }

    // ============================================================
    // Create Enquiry
    // ============================================================

    @Override
    public B2BEnquiryResponseDTO createEnquiry(B2BEnquiryRequestDTO request) {

        B2BEnquiry enquiry = new B2BEnquiry();

        enquiry.setCompanyName(request.getCompanyName());
        enquiry.setContactPerson(request.getContactPerson());
        enquiry.setEmail(request.getEmail());
        enquiry.setMobileNumber(request.getMobileNumber());
        enquiry.setGstNumber(request.getGstNumber());
        enquiry.setBusinessType(request.getBusinessType());
        enquiry.setMessage(request.getMessage());

        enquiry.setStatus(EnquiryStatus.NEW);

        enquiry.setCreatedAt(LocalDateTime.now());
        enquiry.setUpdatedAt(LocalDateTime.now());

        B2BEnquiry saved = enquiryRepository.save(enquiry);

        return mapToResponse(saved);
    }

    // ============================================================
    // Get By ID
    // ============================================================

    @Override
    public B2BEnquiryResponseDTO getEnquiryById(Long enquiryId) {

        B2BEnquiry enquiry = enquiryRepository.findById(enquiryId)
                .orElseThrow(() ->
                        new RuntimeException("B2B Enquiry not found"));

        return mapToResponse(enquiry);
    }

    // ============================================================
    // Get All
    // ============================================================

    @Override
    public List<B2BEnquiryResponseDTO> getAllEnquiries() {

        return enquiryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // Get By Status
    // ============================================================

    @Override
    public List<B2BEnquiryResponseDTO> getEnquiriesByStatus(EnquiryStatus status) {

        return enquiryRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // Get By Email
    // ============================================================

    @Override
    public List<B2BEnquiryResponseDTO> getEnquiriesByEmail(String email) {

        return enquiryRepository.findByEmail(email)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // Update Status
    // ============================================================

    @Override
    public B2BEnquiryResponseDTO updateStatus(Long enquiryId,
                                              EnquiryStatus status) {

        B2BEnquiry enquiry = enquiryRepository.findById(enquiryId)
                .orElseThrow(() ->
                        new RuntimeException("B2B Enquiry not found"));

        enquiry.setStatus(status);
        enquiry.setUpdatedAt(LocalDateTime.now());

        B2BEnquiry updated = enquiryRepository.save(enquiry);

        return mapToResponse(updated);
    }

    // ============================================================
    // Delete
    // ============================================================

    @Override
    public void deleteEnquiry(Long enquiryId) {

        if (!enquiryRepository.existsById(enquiryId)) {
            throw new RuntimeException("B2B Enquiry not found");
        }

        enquiryRepository.deleteById(enquiryId);
    }

    // ============================================================
    // Entity -> DTO Mapper
    // ============================================================

    private B2BEnquiryResponseDTO mapToResponse(B2BEnquiry enquiry) {

        B2BEnquiryResponseDTO dto = new B2BEnquiryResponseDTO();

        dto.setEnquiryId(enquiry.getEnquiryId());
        dto.setCompanyName(enquiry.getCompanyName());
        dto.setContactPerson(enquiry.getContactPerson());
        dto.setEmail(enquiry.getEmail());
        dto.setMobileNumber(enquiry.getMobileNumber());
        dto.setGstNumber(enquiry.getGstNumber());
        dto.setBusinessType(enquiry.getBusinessType());
        dto.setMessage(enquiry.getMessage());
        dto.setStatus(enquiry.getStatus());
        dto.setCreatedAt(enquiry.getCreatedAt());
        dto.setUpdatedAt(enquiry.getUpdatedAt());

        return dto;
    }
}