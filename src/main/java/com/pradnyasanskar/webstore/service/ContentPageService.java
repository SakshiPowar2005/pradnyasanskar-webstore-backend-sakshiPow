package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.ContentPageRequestDTO;
import com.pradnyasanskar.webstore.dto.ContentPageResponseDTO;
import com.pradnyasanskar.webstore.entity.PageStatus;

import java.util.List;

public interface ContentPageService {

    // ============================================================
    // Create Page
    // ============================================================

    ContentPageResponseDTO createPage(ContentPageRequestDTO request);

    // ============================================================
    // Get Page By Id
    // ============================================================

    ContentPageResponseDTO getPageById(Long pageId);

    // ============================================================
    // Get Page By Slug
    // ============================================================

    ContentPageResponseDTO getPageBySlug(String slug);

    // ============================================================
    // Get All Pages
    // ============================================================

    List<ContentPageResponseDTO> getAllPages();

    // ============================================================
    // Get Pages By Status
    // ============================================================

    List<ContentPageResponseDTO> getPagesByStatus(PageStatus status);

    // ============================================================
    // Update Page
    // ============================================================

    ContentPageResponseDTO updatePage(
            Long pageId,
            ContentPageRequestDTO request);

    // ============================================================
    // Delete Page
    // ============================================================

    void deletePage(Long pageId);

}