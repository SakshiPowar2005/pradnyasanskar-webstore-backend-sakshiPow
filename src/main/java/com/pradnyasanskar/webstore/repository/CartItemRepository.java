package com.pradnyasanskar.webstore.repository;

import com.pradnyasanskar.webstore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartCartIdAndProductVariantVariantId(
            Long cartId,
            Long variantId);

    List<CartItem> findByCartCartId(Long cartId);

    void deleteByCartCartId(Long cartId);

}