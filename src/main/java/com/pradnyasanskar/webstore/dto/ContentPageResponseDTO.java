package com.pradnyasanskar.webstore.dto;

import com.pradnyasanskar.webstore.entity.PageStatus;

import java.time.LocalDateTime;

/**
 * DTO returned in Content Page API responses.
 */
public class ContentPageResponseDTO {

    // ============================================================
    // Page Information
    // ============================================================

    private Long pageId;

    private String title;

    private String slug;

    private String content;

    private String metaTitle;

    private String metaDescription;

    // ============================================================
    // Status
    // ============================================================

    private PageStatus status;

    // ============================================================
    // Updated By
    // ============================================================

    private Long updatedBy;

    private String updatedByName;

    // ============================================================
    // Audit Fields
    // ============================================================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ContentPageResponseDTO() {
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public PageStatus getStatus() {
        return status;
    }

    public void setStatus(PageStatus status) {
        this.status = status;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedByName() {
        return updatedByName;
    }

    public void setUpdatedByName(String updatedByName) {
        this.updatedByName = updatedByName;
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