package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.ContentPage;
import com.pradnyasanskar.webstore.entity.PageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentPageRepository extends JpaRepository<ContentPage, Long> {

    // ============================================================
    // Find Page By Slug
    // ============================================================

    Optional<ContentPage> findBySlug(String slug);

    // ============================================================
    // Find Pages By Status
    // ============================================================

    List<ContentPage> findByStatus(PageStatus status);

    // ============================================================
    // Check Slug Exists
    // ============================================================

    boolean existsBySlug(String slug);

}