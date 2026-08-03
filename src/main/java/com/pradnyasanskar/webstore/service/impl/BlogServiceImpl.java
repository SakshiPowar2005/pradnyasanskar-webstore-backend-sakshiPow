package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.BlogRequestDTO;
import com.pradnyasanskar.webstore.dto.BlogResponseDTO;
import com.pradnyasanskar.webstore.entity.Blog;
import com.pradnyasanskar.webstore.entity.BlogStatus;
import com.pradnyasanskar.webstore.entity.User;
import com.pradnyasanskar.webstore.repository.BlogRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.service.BlogService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public BlogServiceImpl(BlogRepository blogRepository,
                           UserRepository userRepository) {

        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    // ============================================================
    // Create Blog
    // ============================================================

    @Override
    public BlogResponseDTO createBlog(BlogRequestDTO request) {

        if (blogRepository.existsBySlug(request.getSlug())) {
            throw new RuntimeException("Slug already exists.");
        }

        User author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() ->
                        new RuntimeException("Author not found."));

        Blog blog = new Blog();

        blog.setTitle(request.getTitle());
        blog.setSlug(request.getSlug());
        blog.setFeaturedImageUrl(request.getFeaturedImageUrl());
        blog.setSummary(request.getSummary());
        blog.setContent(request.getContent());
        blog.setAuthor(author);
        blog.setStatus(request.getStatus());

        // Publish date only when published
        if (request.getStatus() == BlogStatus.PUBLISHED) {
            blog.setPublishedAt(LocalDateTime.now());
        }

        Blog savedBlog = blogRepository.save(blog);

        return mapToResponse(savedBlog);
    }

    // ============================================================
    // Get Blog By Id
    // ============================================================

    @Override
    public BlogResponseDTO getBlogById(Long blogId) {

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() ->
                        new RuntimeException("Blog not found."));

        return mapToResponse(blog);
    }

    // ============================================================
    // Get All Blogs
    // ============================================================

    @Override
    public List<BlogResponseDTO> getAllBlogs() {

        return blogRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // Get Blogs By Status
    // ============================================================

    @Override
    public List<BlogResponseDTO> getBlogsByStatus(BlogStatus status) {

        return blogRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // Get Blogs By Author
    // ============================================================

    @Override
    public List<BlogResponseDTO> getBlogsByAuthor(Long authorId) {

        return blogRepository.findByAuthor_UserId(authorId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // Update Blog
    // ============================================================

    @Override
    public BlogResponseDTO updateBlog(Long blogId,
                                      BlogRequestDTO request) {

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() ->
                        new RuntimeException("Blog not found."));

        // Check duplicate slug
        if (!blog.getSlug().equals(request.getSlug())
                && blogRepository.existsBySlug(request.getSlug())) {

            throw new RuntimeException("Slug already exists.");
        }

        User author = userRepository.findById(request.getAuthorId())
                .orElseThrow(() ->
                        new RuntimeException("Author not found."));

        blog.setTitle(request.getTitle());
        blog.setSlug(request.getSlug());
        blog.setFeaturedImageUrl(request.getFeaturedImageUrl());
        blog.setSummary(request.getSummary());
        blog.setContent(request.getContent());
        blog.setAuthor(author);

        // Set publish time only once
        if (blog.getStatus() != BlogStatus.PUBLISHED
                && request.getStatus() == BlogStatus.PUBLISHED) {

            blog.setPublishedAt(LocalDateTime.now());
        }

        blog.setStatus(request.getStatus());

        Blog updatedBlog = blogRepository.save(blog);

        return mapToResponse(updatedBlog);
    }

    // ============================================================
    // Delete Blog
    // ============================================================

    @Override
    public void deleteBlog(Long blogId) {

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() ->
                        new RuntimeException("Blog not found."));

        blogRepository.delete(blog);
    }

    // ============================================================
    // Entity -> DTO Mapper
    // ============================================================

    private BlogResponseDTO mapToResponse(Blog blog) {

        BlogResponseDTO dto = new BlogResponseDTO();

        dto.setBlogId(blog.getBlogId());
        dto.setTitle(blog.getTitle());
        dto.setSlug(blog.getSlug());
        dto.setFeaturedImageUrl(blog.getFeaturedImageUrl());
        dto.setSummary(blog.getSummary());
        dto.setContent(blog.getContent());

        if (blog.getAuthor() != null) {

            dto.setAuthorId(blog.getAuthor().getUserId());

            dto.setAuthorName(
                    blog.getAuthor().getFirstName() + " "
                            + blog.getAuthor().getLastName()
            );
        }

        dto.setStatus(blog.getStatus());
        dto.setPublishedAt(blog.getPublishedAt());
        dto.setCreatedAt(blog.getCreatedAt());
        dto.setUpdatedAt(blog.getUpdatedAt());

        return dto;
    }
}