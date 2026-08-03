package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.B2BEnquiryRequestDTO;
import com.pradnyasanskar.webstore.dto.B2BEnquiryResponseDTO;
import com.pradnyasanskar.webstore.entity.EnquiryStatus;

import java.util.List;

public interface B2BEnquiryService {

    B2BEnquiryResponseDTO createEnquiry(B2BEnquiryRequestDTO request);

    B2BEnquiryResponseDTO getEnquiryById(Long enquiryId);

    List<B2BEnquiryResponseDTO> getAllEnquiries();

    List<B2BEnquiryResponseDTO> getEnquiriesByStatus(EnquiryStatus status);

    List<B2BEnquiryResponseDTO> getEnquiriesByEmail(String email);

    B2BEnquiryResponseDTO updateStatus(Long enquiryId,
                                       EnquiryStatus status);

    void deleteEnquiry(Long enquiryId);

}