package com.example.core.dto.request;

import lombok.Data;

import javax.persistence.Column;

@Data
public class CartItemRequest {
    private Long variantId;
    private Long quantity;
}
