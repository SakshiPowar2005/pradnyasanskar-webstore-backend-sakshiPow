package com.pradnyasanskar.webstore.dto;

public class OrderItemRequestDTO {

    private Long variantId;
    private Integer quantity;

    public OrderItemRequestDTO() {
    }

    public Long getVariantId() {
        return variantId;
    }

    public void setVariantId(Long variantId) {
        this.variantId = variantId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}