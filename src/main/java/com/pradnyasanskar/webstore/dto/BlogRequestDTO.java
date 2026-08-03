package com.pradnyasanskar.webstore.dto;

import com.pradnyasanskar.webstore.entity.BlogStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO used while creating/updating a Blog.
 */
public class BlogRequestDTO {

    // ============================================================
    // Blog Details
    // ============================================================

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Slug is required.")
    private String slug;

    private String featuredImageUrl;

    private String summary;

    @NotBlank(message = "Content is required.")
    private String content;

    // ============================================================
    // Author
    // ============================================================

    @NotNull(message = "Author Id is required.")
    private Long authorId;

    // ============================================================
    // Blog Status
    // ============================================================

    @NotNull(message = "Status is required.")
    private BlogStatus status;

    // ============================================================
    // Constructors
    // ============================================================

    public BlogRequestDTO() {
    }

    // ============================================================
    // Getters & Setters
    // ============================================================

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

    public String getFeaturedImageUrl() {
        return featuredImageUrl;
    }

    public void setFeaturedImageUrl(String featuredImageUrl) {
        this.featuredImageUrl = featuredImageUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public BlogStatus getStatus() {
        return status;
    }

    public void setStatus(BlogStatus status) {
        this.status = status;
    }
}