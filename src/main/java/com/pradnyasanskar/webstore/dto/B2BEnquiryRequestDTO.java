package com.pradnyasanskar.webstore.dto;

public class B2BEnquiryRequestDTO {

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

    // ============================================================
    // Constructors
    // ============================================================

    public B2BEnquiryRequestDTO() {
    }

    // ============================================================
    // Getters & Setters
    // ============================================================

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
}