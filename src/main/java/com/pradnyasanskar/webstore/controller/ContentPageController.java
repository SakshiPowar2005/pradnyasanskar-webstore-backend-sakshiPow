package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.ContentPageRequestDTO;
import com.pradnyasanskar.webstore.dto.ContentPageResponseDTO;
import com.pradnyasanskar.webstore.entity.PageStatus;
import com.pradnyasanskar.webstore.service.ContentPageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content-pages")
public class ContentPageController {

    private final ContentPageService contentPageService;

    public ContentPageController(ContentPageService contentPageService) {
        this.contentPageService = contentPageService;
    }

    // ============================================================
    // Create Page
    // ============================================================

    @PostMapping
    public ResponseEntity<ContentPageResponseDTO> createPage(
            @Valid @RequestBody ContentPageRequestDTO request) {

        return new ResponseEntity<>(
                contentPageService.createPage(request),
                HttpStatus.CREATED
        );
    }

    // ============================================================
    // Get Page By Id
    // ============================================================

    @GetMapping("/{pageId}")
    public ResponseEntity<ContentPageResponseDTO> getPageById(
            @PathVariable Long pageId) {

        return ResponseEntity.ok(
                contentPageService.getPageById(pageId)
        );
    }

    // ============================================================
    // Get Page By Slug
    // ============================================================

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ContentPageResponseDTO> getPageBySlug(
            @PathVariable String slug) {

        return ResponseEntity.ok(
                contentPageService.getPageBySlug(slug)
        );
    }

    // ============================================================
    // Get All Pages
    // ============================================================

    @GetMapping
    public ResponseEntity<List<ContentPageResponseDTO>> getAllPages() {

        return ResponseEntity.ok(
                contentPageService.getAllPages()
        );
    }

    // ============================================================
    // Get Pages By Status
    // ============================================================

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ContentPageResponseDTO>> getPagesByStatus(
            @PathVariable PageStatus status) {

        return ResponseEntity.ok(
                contentPageService.getPagesByStatus(status)
        );
    }

    // ============================================================
    // Update Page
    // ============================================================

    @PutMapping("/{pageId}")
    public ResponseEntity<ContentPageResponseDTO> updatePage(
            @PathVariable Long pageId,
            @Valid @RequestBody ContentPageRequestDTO request) {

        return ResponseEntity.ok(
                contentPageService.updatePage(pageId, request)
        );
    }

    // ============================================================
    // Delete Page
    // ============================================================

    @DeleteMapping("/{pageId}")
    public ResponseEntity<String> deletePage(
            @PathVariable Long pageId) {

        contentPageService.deletePage(pageId);

        return ResponseEntity.ok("Content page deleted successfully.");
    }
}