package com.pradnyasanskar.webstore.dto;

import com.pradnyasanskar.webstore.entity.PageStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO used for creating/updating CMS Content Pages.
 */
public class ContentPageRequestDTO {

    // ============================================================
    // Page Details
    // ============================================================

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Slug is required.")
    private String slug;

    @NotBlank(message = "Content is required.")
    private String content;

    private String metaTitle;

    private String metaDescription;

    // ============================================================
    // Status
    // ============================================================

    @NotNull(message = "Status is required.")
    private PageStatus status;

    // ============================================================
    // Updated By
    // ============================================================

    private Long updatedBy;

    public ContentPageRequestDTO() {
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
}