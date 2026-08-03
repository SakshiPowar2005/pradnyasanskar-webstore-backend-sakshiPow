package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.ContentPageRequestDTO;
import com.pradnyasanskar.webstore.dto.ContentPageResponseDTO;
import com.pradnyasanskar.webstore.entity.ContentPage;
import com.pradnyasanskar.webstore.entity.User;
import com.pradnyasanskar.webstore.repository.ContentPageRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.service.ContentPageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentPageServiceImpl implements ContentPageService {

    private final ContentPageRepository contentPageRepository;
    private final UserRepository userRepository;

    public ContentPageServiceImpl(ContentPageRepository contentPageRepository,
                                  UserRepository userRepository) {

        this.contentPageRepository = contentPageRepository;
        this.userRepository = userRepository;
    }

    // ============================================================
    // Create Page
    // ============================================================

    @Override
    public ContentPageResponseDTO createPage(ContentPageRequestDTO request) {

        if (contentPageRepository.existsBySlug(request.getSlug())) {
            throw new RuntimeException("Slug already exists.");
        }

        ContentPage page = new ContentPage();

        page.setTitle(request.getTitle());
        page.setSlug(request.getSlug());
        page.setContent(request.getContent());
        page.setMetaTitle(request.getMetaTitle());
        page.setMetaDescription(request.getMetaDescription());
        page.setStatus(request.getStatus());

        if (request.getUpdatedBy() != null) {

            User user = userRepository.findById(request.getUpdatedBy())
                    .orElseThrow(() ->
                            new RuntimeException("User not found."));

            page.setUpdatedBy(user);
        }

        return mapToResponse(contentPageRepository.save(page));
    }

    // ============================================================
    // Get Page By Id
    // ============================================================

    @Override
    public ContentPageResponseDTO getPageById(Long pageId) {

        ContentPage page = contentPageRepository.findById(pageId)
                .orElseThrow(() ->
                        new RuntimeException("Content page not found."));

        return mapToResponse(page);
    }

    // ============================================================
    // Get Page By Slug
    // ============================================================

    @Override
    public ContentPageResponseDTO getPageBySlug(String slug) {

        ContentPage page = contentPageRepository.findBySlug(slug)
                .orElseThrow(() ->
                        new RuntimeException("Content page not found."));

        return mapToResponse(page);
    }

    // ============================================================
    // Get All Pages
    // ============================================================

    @Override
    public List<ContentPageResponseDTO> getAllPages() {

        return contentPageRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // Get Pages By Status
    // ============================================================

    @Override
    public List<ContentPageResponseDTO> getPagesByStatus(
            com.pradnyasanskar.webstore.entity.PageStatus status) {

        return contentPageRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // Update Page
    // ============================================================

    @Override
    public ContentPageResponseDTO updatePage(Long pageId,
                                             ContentPageRequestDTO request) {

        ContentPage page = contentPageRepository.findById(pageId)
                .orElseThrow(() ->
                        new RuntimeException("Content page not found."));

        if (!page.getSlug().equals(request.getSlug())
                && contentPageRepository.existsBySlug(request.getSlug())) {

            throw new RuntimeException("Slug already exists.");
        }

        page.setTitle(request.getTitle());
        page.setSlug(request.getSlug());
        page.setContent(request.getContent());
        page.setMetaTitle(request.getMetaTitle());
        page.setMetaDescription(request.getMetaDescription());
        page.setStatus(request.getStatus());

        if (request.getUpdatedBy() != null) {

            User user = userRepository.findById(request.getUpdatedBy())
                    .orElseThrow(() ->
                            new RuntimeException("User not found."));

            page.setUpdatedBy(user);
        }

        return mapToResponse(contentPageRepository.save(page));
    }

    // ============================================================
    // Delete Page
    // ============================================================

    @Override
    public void deletePage(Long pageId) {

        ContentPage page = contentPageRepository.findById(pageId)
                .orElseThrow(() ->
                        new RuntimeException("Content page not found."));

        contentPageRepository.delete(page);
    }

    // ============================================================
    // Entity → DTO Mapper
    // ============================================================

    private ContentPageResponseDTO mapToResponse(ContentPage page) {

        ContentPageResponseDTO dto = new ContentPageResponseDTO();

        dto.setPageId(page.getPageId());
        dto.setTitle(page.getTitle());
        dto.setSlug(page.getSlug());
        dto.setContent(page.getContent());
        dto.setMetaTitle(page.getMetaTitle());
        dto.setMetaDescription(page.getMetaDescription());
        dto.setStatus(page.getStatus());

        if (page.getUpdatedBy() != null) {

            dto.setUpdatedBy(page.getUpdatedBy().getUserId());

            dto.setUpdatedByName(
                    page.getUpdatedBy().getFirstName() + " "
                            + page.getUpdatedBy().getLastName()
            );
        }

        dto.setCreatedAt(page.getCreatedAt());
        dto.setUpdatedAt(page.getUpdatedAt());

        return dto;
    }
}