package com.pradnyasanskar.webstore.dto;

public class FAQRequestDTO {

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
    // Constructors
    // ============================================================

    public FAQRequestDTO() {
    }

    // ============================================================
    // Getters & Setters
    // ============================================================

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
}