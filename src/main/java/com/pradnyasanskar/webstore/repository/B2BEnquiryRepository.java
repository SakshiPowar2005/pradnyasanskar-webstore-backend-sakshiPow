package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.B2BEnquiry;
import com.pradnyasanskar.webstore.entity.EnquiryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface B2BEnquiryRepository extends JpaRepository<B2BEnquiry, Long> {

    List<B2BEnquiry> findByStatus(EnquiryStatus status);

    List<B2BEnquiry> findByEmail(String email);

    List<B2BEnquiry> findByCompanyNameContainingIgnoreCase(String companyName);

}