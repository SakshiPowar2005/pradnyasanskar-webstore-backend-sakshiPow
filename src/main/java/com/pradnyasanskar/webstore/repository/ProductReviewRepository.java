package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.ProductReview;
import com.pradnyasanskar.webstore.entity.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
public interface ProductReviewRepository
        extends JpaRepository<ProductReview, Long> {

    List<ProductReview> findByProduct_ProductId(
            Long productId);

    List<ProductReview> findByUser_UserId(
            Long userId);

    List<ProductReview> findByReviewStatus(
            ReviewStatus reviewStatus);

    List<ProductReview> findByProduct_ProductIdAndReviewStatus(
            Long productId,
            ReviewStatus reviewStatus);



    // ============================================================
// Average Rating
// ============================================================

    @Query("""
SELECT AVG(r.rating)
FROM ProductReview r
""")
    Double getAverageRating();
}