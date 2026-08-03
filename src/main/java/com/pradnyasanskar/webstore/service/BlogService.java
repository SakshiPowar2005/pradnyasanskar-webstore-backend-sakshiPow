package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.BlogRequestDTO;
import com.pradnyasanskar.webstore.dto.BlogResponseDTO;
import com.pradnyasanskar.webstore.entity.BlogStatus;

import java.util.List;

public interface BlogService {

    // ============================================================
    // Create Blog
    // ============================================================

    BlogResponseDTO createBlog(BlogRequestDTO request);

    // ============================================================
    // Get Blog By Id
    // ============================================================

    BlogResponseDTO getBlogById(Long blogId);

    // ============================================================
    // Get All Blogs
    // ============================================================

    List<BlogResponseDTO> getAllBlogs();

    // ============================================================
    // Get Blogs By Status
    // ============================================================

    List<BlogResponseDTO> getBlogsByStatus(BlogStatus status);

    // ============================================================
    // Get Blogs By Author
    // ============================================================

    List<BlogResponseDTO> getBlogsByAuthor(Long authorId);

    // ============================================================
    // Update Blog
    // ============================================================

    BlogResponseDTO updateBlog(Long blogId,
                               BlogRequestDTO request);

    // ============================================================
    // Delete Blog
    // ============================================================

    void deleteBlog(Long blogId);

}