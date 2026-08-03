package com.pradnyasanskar.webstore.dto;

import com.pradnyasanskar.webstore.entity.BlogStatus;

import java.time.LocalDateTime;

/**
 * DTO returned in Blog API responses.
 */
public class BlogResponseDTO {

    // ============================================================
    // Blog Information
    // ============================================================

    private Long blogId;

    private String title;

    private String slug;

    private String featuredImageUrl;

    private String summary;

    private String content;

    // ============================================================
    // Author Information
    // ============================================================

    private Long authorId;

    private String authorName;

    // ============================================================
    // Blog Status
    // ============================================================

    private BlogStatus status;

    // ============================================================
    // Dates
    // ============================================================

    private LocalDateTime publishedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ============================================================
    // Constructors
    // ============================================================

    public BlogResponseDTO() {
    }

    // ============================================================
    // Getters & Setters
    // ============================================================

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public BlogStatus getStatus() {
        return status;
    }

    public void setStatus(BlogStatus status) {
        this.status = status;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
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