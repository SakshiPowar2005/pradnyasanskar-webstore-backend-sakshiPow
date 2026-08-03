package com.pradnyasanskar.webstore.service.impl;

import com.pradnyasanskar.webstore.dto.ProductReviewRequestDTO;
import com.pradnyasanskar.webstore.dto.ProductReviewResponseDTO;
import com.pradnyasanskar.webstore.entity.*;
import com.pradnyasanskar.webstore.repository.OrderItemRepository;
import com.pradnyasanskar.webstore.repository.ProductRepository;
import com.pradnyasanskar.webstore.repository.ProductReviewRepository;
import com.pradnyasanskar.webstore.repository.UserRepository;
import com.pradnyasanskar.webstore.service.ProductReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    public ProductReviewServiceImpl(
            ProductReviewRepository productReviewRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            OrderItemRepository orderItemRepository) {

        this.productReviewRepository = productReviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // ============================================================
    // CREATE REVIEW
    // ============================================================

    @Override
    public ProductReviewResponseDTO createReview(
            ProductReviewRequestDTO request) {

        Product product = productRepository.findById(
                        request.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found."));

        User user = userRepository.findById(
                        request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found."));

        ProductReview review = new ProductReview();

        review.setProduct(product);

        review.setUser(user);

        if (request.getOrderItemId() != null) {

            OrderItem orderItem = orderItemRepository.findById(
                            request.getOrderItemId())
                    .orElseThrow(() ->
                            new RuntimeException("Order Item not found."));

            review.setOrderItem(orderItem);

            review.setIsVerifiedPurchase(true);
        }

        review.setRating(request.getRating());

        review.setReviewTitle(request.getReviewTitle());

        review.setReviewText(request.getReviewText());

        if (request.getIsVerifiedPurchase() != null) {
            review.setIsVerifiedPurchase(
                    request.getIsVerifiedPurchase());
        }

        ProductReview saved =
                productReviewRepository.save(review);

        return mapToResponse(saved);
    }

    // ============================================================
    // GET REVIEW BY ID
    // ============================================================

    @Override
    public ProductReviewResponseDTO getReviewById(
            Long reviewId) {

        ProductReview review =
                productReviewRepository.findById(reviewId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Review not found."));

        return mapToResponse(review);
    }
    // ============================================================
    // GET ALL REVIEWS
    // ============================================================

    @Override
    public List<ProductReviewResponseDTO> getAllReviews() {

        return productReviewRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET REVIEWS BY PRODUCT
    // ============================================================

    @Override
    public List<ProductReviewResponseDTO> getReviewsByProduct(
            Long productId) {

        return productReviewRepository
                .findByProduct_ProductId(productId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET REVIEWS BY USER
    // ============================================================

    @Override
    public List<ProductReviewResponseDTO> getReviewsByUser(
            Long userId) {

        return productReviewRepository
                .findByUser_UserId(userId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET REVIEWS BY STATUS
    // ============================================================

    @Override
    public List<ProductReviewResponseDTO> getReviewsByStatus(
            ReviewStatus status) {

        return productReviewRepository
                .findByReviewStatus(status)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ============================================================
    // APPROVE REVIEW
    // ============================================================

    @Override
    public ProductReviewResponseDTO approveReview(
            Long reviewId) {

        ProductReview review =
                productReviewRepository.findById(reviewId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Review not found."));

        review.setReviewStatus(
                ReviewStatus.APPROVED);

        ProductReview updated =
                productReviewRepository.save(review);

        return mapToResponse(updated);
    }

    // ============================================================
    // REJECT REVIEW
    // ============================================================

    @Override
    public ProductReviewResponseDTO rejectReview(
            Long reviewId) {

        ProductReview review =
                productReviewRepository.findById(reviewId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Review not found."));

        review.setReviewStatus(
                ReviewStatus.REJECTED);

        ProductReview updated =
                productReviewRepository.save(review);

        return mapToResponse(updated);
    }
    // ============================================================
    // UPDATE REVIEW
    // ============================================================

    @Override
    public ProductReviewResponseDTO updateReview(
            Long reviewId,
            ProductReviewRequestDTO request) {

        ProductReview review =
                productReviewRepository.findById(reviewId)
                        .orElseThrow(() ->
                                new RuntimeException("Review not found."));

        if (request.getRating() != null) {
            review.setRating(request.getRating());
        }

        review.setReviewTitle(
                request.getReviewTitle());

        review.setReviewText(
                request.getReviewText());

        if (request.getIsVerifiedPurchase() != null) {
            review.setIsVerifiedPurchase(
                    request.getIsVerifiedPurchase());
        }

        if (request.getOrderItemId() != null) {

            OrderItem orderItem =
                    orderItemRepository.findById(
                                    request.getOrderItemId())
                            .orElseThrow(() ->
                                    new RuntimeException("Order Item not found."));

            review.setOrderItem(orderItem);
        }

        ProductReview updated =
                productReviewRepository.save(review);

        return mapToResponse(updated);
    }

    // ============================================================
    // DELETE REVIEW
    // ============================================================

    @Override
    public void deleteReview(
            Long reviewId) {

        ProductReview review =
                productReviewRepository.findById(reviewId)
                        .orElseThrow(() ->
                                new RuntimeException("Review not found."));

        productReviewRepository.delete(review);
    }

    // ============================================================
    // MAP ENTITY TO DTO
    // ============================================================

    private ProductReviewResponseDTO mapToResponse(
            ProductReview review) {

        ProductReviewResponseDTO response =
                new ProductReviewResponseDTO();

        response.setReviewId(
                review.getReviewId());

        response.setProductId(
                review.getProduct().getProductId());

        response.setProductName(
                review.getProduct().getProductName());

        response.setUserId(
                review.getUser().getUserId());

        response.setUserName(
                review.getUser().getFirstName() + " "
                        + review.getUser().getLastName());

        if (review.getOrderItem() != null) {

            response.setOrderItemId(
                    review.getOrderItem().getOrderItemId());

        }

        response.setRating(
                review.getRating());

        response.setReviewTitle(
                review.getReviewTitle());

        response.setReviewText(
                review.getReviewText());

        response.setReviewStatus(
                review.getReviewStatus());

        response.setIsVerifiedPurchase(
                review.getIsVerifiedPurchase());

        response.setHelpfulCount(
                review.getHelpfulCount());

        response.setCreatedAt(
                review.getCreatedAt());

        response.setUpdatedAt(
                review.getUpdatedAt());

        return response;
    }

}