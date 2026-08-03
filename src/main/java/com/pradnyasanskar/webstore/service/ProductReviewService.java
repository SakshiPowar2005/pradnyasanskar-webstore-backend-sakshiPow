package com.pradnyasanskar.webstore.service;

import com.pradnyasanskar.webstore.dto.ProductReviewRequestDTO;
import com.pradnyasanskar.webstore.dto.ProductReviewResponseDTO;
import com.pradnyasanskar.webstore.entity.ReviewStatus;

import java.util.List;

public interface ProductReviewService {

    // ============================================================
    // CREATE REVIEW
    // ============================================================

    ProductReviewResponseDTO createReview(
            ProductReviewRequestDTO request);

    // ============================================================
    // GET REVIEW BY ID
    // ============================================================

    ProductReviewResponseDTO getReviewById(
            Long reviewId);

    // ============================================================
    // GET ALL REVIEWS
    // ============================================================

    List<ProductReviewResponseDTO> getAllReviews();

    // ============================================================
    // GET REVIEWS OF PRODUCT
    // ============================================================

    List<ProductReviewResponseDTO> getReviewsByProduct(
            Long productId);

    // ============================================================
    // GET REVIEWS OF USER
    // ============================================================

    List<ProductReviewResponseDTO> getReviewsByUser(
            Long userId);

    // ============================================================
    // GET REVIEWS BY STATUS
    // ============================================================

    List<ProductReviewResponseDTO> getReviewsByStatus(
            ReviewStatus status);

    // ============================================================
    // APPROVE REVIEW
    // ============================================================

    ProductReviewResponseDTO approveReview(
            Long reviewId);

    // ============================================================
    // REJECT REVIEW
    // ============================================================

    ProductReviewResponseDTO rejectReview(
            Long reviewId);

    // ============================================================
    // UPDATE REVIEW
    // ============================================================

    ProductReviewResponseDTO updateReview(
            Long reviewId,
            ProductReviewRequestDTO request);

    // ============================================================
    // DELETE REVIEW
    // ============================================================

    void deleteReview(
            Long reviewId);

}