package com.pradnyasanskar.webstore.controller;

import com.pradnyasanskar.webstore.dto.ProductReviewRequestDTO;
import com.pradnyasanskar.webstore.dto.ProductReviewResponseDTO;
import com.pradnyasanskar.webstore.entity.ReviewStatus;
import com.pradnyasanskar.webstore.service.ProductReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    public ProductReviewController(
            ProductReviewService productReviewService) {

        this.productReviewService = productReviewService;
    }

    // ============================================================
    // CREATE REVIEW
    // ============================================================

    @PostMapping
    public ResponseEntity<ProductReviewResponseDTO> createReview(
            @RequestBody ProductReviewRequestDTO request) {

        return new ResponseEntity<>(
                productReviewService.createReview(request),
                HttpStatus.CREATED);
    }

    // ============================================================
    // GET REVIEW BY ID
    // ============================================================

    @GetMapping("/{reviewId}")
    public ResponseEntity<ProductReviewResponseDTO> getReviewById(
            @PathVariable Long reviewId) {

        return ResponseEntity.ok(
                productReviewService.getReviewById(reviewId));
    }

    // ============================================================
    // GET ALL REVIEWS
    // ============================================================

    @GetMapping
    public ResponseEntity<List<ProductReviewResponseDTO>>
    getAllReviews() {

        return ResponseEntity.ok(
                productReviewService.getAllReviews());
    }

    // ============================================================
    // GET REVIEWS BY PRODUCT
    // ============================================================

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ProductReviewResponseDTO>>
    getReviewsByProduct(
            @PathVariable Long productId) {

        return ResponseEntity.ok(
                productReviewService.getReviewsByProduct(productId));
    }

    // ============================================================
    // GET REVIEWS BY USER
    // ============================================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProductReviewResponseDTO>>
    getReviewsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                productReviewService.getReviewsByUser(userId));
    }

    // ============================================================
    // GET REVIEWS BY STATUS
    // ============================================================

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProductReviewResponseDTO>>
    getReviewsByStatus(
            @PathVariable ReviewStatus status) {

        return ResponseEntity.ok(
                productReviewService.getReviewsByStatus(status));
    }

    // ============================================================
    // APPROVE REVIEW
    // ============================================================

    @PutMapping("/{reviewId}/approve")
    public ResponseEntity<ProductReviewResponseDTO> approveReview(
            @PathVariable Long reviewId) {

        return ResponseEntity.ok(
                productReviewService.approveReview(reviewId));
    }

    // ============================================================
    // REJECT REVIEW
    // ============================================================

    @PutMapping("/{reviewId}/reject")
    public ResponseEntity<ProductReviewResponseDTO> rejectReview(
            @PathVariable Long reviewId) {

        return ResponseEntity.ok(
                productReviewService.rejectReview(reviewId));
    }

    // ============================================================
    // UPDATE REVIEW
    // ============================================================

    @PutMapping("/{reviewId}")
    public ResponseEntity<ProductReviewResponseDTO> updateReview(
            @PathVariable Long reviewId,
            @RequestBody ProductReviewRequestDTO request) {

        return ResponseEntity.ok(
                productReviewService.updateReview(
                        reviewId,
                        request));
    }

    // ============================================================
    // DELETE REVIEW
    // ============================================================

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long reviewId) {

        productReviewService.deleteReview(reviewId);

        return ResponseEntity.ok(
                "Review deleted successfully.");
    }

}