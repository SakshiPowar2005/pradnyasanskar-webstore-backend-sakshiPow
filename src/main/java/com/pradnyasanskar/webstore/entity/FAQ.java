package com.pradnyasanskar.webstore.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "faqs")
public class FAQ {

    // ============================================================
    // Primary Key
    // ============================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_id")
    private Long faqId;

    // ============================================================
    // FAQ Question
    // ============================================================

    @Column(name = "question", nullable = false, columnDefinition = "TEXT")
    private String question;

    // ============================================================
    // FAQ Answer
    // ============================================================

    @Column(name = "answer", nullable = false, columnDefinition = "TEXT")
    private String answer;

    // ============================================================
    // Display Order
    // ============================================================

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    // ============================================================
    // Active Status
    // ============================================================

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    // ============================================================
    // Audit Fields
    // ============================================================

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // ============================================================
    // Constructors
    // ============================================================

    public FAQ() {
    }

    // ============================================================
    // Getters and Setters
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