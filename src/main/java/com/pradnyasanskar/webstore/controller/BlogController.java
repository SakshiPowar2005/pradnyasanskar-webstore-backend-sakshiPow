package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.BlogRequestDTO;
import com.pradnyasanskar.webstore.dto.BlogResponseDTO;
import com.pradnyasanskar.webstore.entity.BlogStatus;
import com.pradnyasanskar.webstore.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    // ============================================================
    // Create Blog
    // ============================================================

    @PostMapping
    public ResponseEntity<BlogResponseDTO> createBlog(
            @Valid @RequestBody BlogRequestDTO request) {

        return new ResponseEntity<>(
                blogService.createBlog(request),
                HttpStatus.CREATED
        );
    }

    // ============================================================
    // Get Blog By Id
    // ============================================================

    @GetMapping("/{blogId}")
    public ResponseEntity<BlogResponseDTO> getBlogById(
            @PathVariable Long blogId) {

        return ResponseEntity.ok(
                blogService.getBlogById(blogId)
        );
    }

    // ============================================================
    // Get All Blogs
    // ============================================================

    @GetMapping
    public ResponseEntity<List<BlogResponseDTO>> getAllBlogs() {

        return ResponseEntity.ok(
                blogService.getAllBlogs()
        );
    }

    // ============================================================
    // Get Blogs By Status
    // ============================================================

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BlogResponseDTO>> getBlogsByStatus(
            @PathVariable BlogStatus status) {

        return ResponseEntity.ok(
                blogService.getBlogsByStatus(status)
        );
    }

    // ============================================================
    // Get Blogs By Author
    // ============================================================

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<BlogResponseDTO>> getBlogsByAuthor(
            @PathVariable Long authorId) {

        return ResponseEntity.ok(
                blogService.getBlogsByAuthor(authorId)
        );
    }

    // ============================================================
    // Update Blog
    // ============================================================

    @PutMapping("/{blogId}")
    public ResponseEntity<BlogResponseDTO> updateBlog(
            @PathVariable Long blogId,
            @Valid @RequestBody BlogRequestDTO request) {

        return ResponseEntity.ok(
                blogService.updateBlog(blogId, request)
        );
    }

    // ============================================================
    // Delete Blog
    // ============================================================

    @DeleteMapping("/{blogId}")
    public ResponseEntity<String> deleteBlog(
            @PathVariable Long blogId) {

        blogService.deleteBlog(blogId);

        return ResponseEntity.ok("Blog deleted successfully.");
    }

}