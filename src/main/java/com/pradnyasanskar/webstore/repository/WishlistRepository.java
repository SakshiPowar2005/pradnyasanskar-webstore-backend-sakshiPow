package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUser_UserId(Long userId);

    Optional<Wishlist> findByUser_UserIdAndProductVariant_VariantId(
            Long userId,
            Long variantId
    );

    boolean existsByUser_UserIdAndProductVariant_VariantId(
            Long userId,
            Long variantId
    );

    void deleteByUser_UserIdAndProductVariant_VariantId(
            Long userId,
            Long variantId
    );
}