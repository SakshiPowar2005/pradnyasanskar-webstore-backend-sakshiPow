package com.pradnyasanskar.webstore.dto;

import com.pradnyasanskar.webstore.entity.EnquiryStatus;

import java.time.LocalDateTime;

public class B2BEnquiryResponseDTO {

    // ============================================================
    // Primary Key
    // ============================================================

    private Long enquiryId;

    // ============================================================
    // Company Details
    // ============================================================

    private String companyName;

    private String contactPerson;

    private String email;

    private String mobileNumber;

    private String gstNumber;

    // ============================================================
    // Enquiry Details
    // ============================================================

    private String businessType;

    private String message;

    private EnquiryStatus status;

    // ============================================================
    // Audit Fields
    // ============================================================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ============================================================
    // Constructors
    // ============================================================

    public B2BEnquiryResponseDTO() {
    }

    // ============================================================
    // Getters & Setters
    // ============================================================

    public Long getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Long enquiryId) {
        this.enquiryId = enquiryId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EnquiryStatus getStatus() {
        return status;
    }

    public void setStatus(EnquiryStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}