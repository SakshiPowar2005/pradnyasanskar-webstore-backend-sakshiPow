package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.Blog;
import com.pradnyasanskar.webstore.entity.BlogStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    // ============================================================
    // Find Blog By Slug
    // ============================================================

    Optional<Blog> findBySlug(String slug);

    // ============================================================
    // Find Blogs By Status
    // ============================================================

    List<Blog> findByStatus(BlogStatus status);

    // ============================================================
    // Find Blogs By Author
    // ============================================================

    List<Blog> findByAuthor_UserId(Long userId);

    // ============================================================
    // Check Slug Already Exists
    // ============================================================

    boolean existsBySlug(String slug);

}