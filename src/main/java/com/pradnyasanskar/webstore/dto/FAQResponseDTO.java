package com.pradnyasanskar.webstore.dto;

import java.time.LocalDateTime;

public class FAQResponseDTO {

    // ============================================================
    // FAQ ID
    // ============================================================

    private Long faqId;

    // ============================================================
    // FAQ Question
    // ============================================================

    private String question;

    // ============================================================
    // FAQ Answer
    // ============================================================

    private String answer;

    // ============================================================
    // Display Order
    // ============================================================

    private Integer displayOrder;

    // ============================================================
    // Active Status
    // ============================================================

    private Boolean isActive;

    // ============================================================
    // Audit Fields
    // ============================================================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ============================================================
    // Constructors
    // ============================================================

    public FAQResponseDTO() {
    }

    // ============================================================
    // Getters & Setters
    // ============================================================

    public Long getFaqId() {
        return faqId;
    }

    public void setFaqId(Long faqId) {
        this.faqId = faqId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
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