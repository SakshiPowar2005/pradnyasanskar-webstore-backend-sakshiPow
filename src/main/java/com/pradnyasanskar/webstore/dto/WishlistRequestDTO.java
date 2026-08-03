package com.pradnyasanskar.webstore.dto;

public class WishlistRequestDTO {

    private Long userId;

    private Long variantId;

    public WishlistRequestDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }
}