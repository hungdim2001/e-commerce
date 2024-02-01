package com.example.core.dto;

import com.example.core.entity.AuditTable;
import lombok.Data;

@Data
public class CartItemDTO extends AuditTable {
    private Long cartId;
    private String name;
    private VariantDTO variantDTO;
    private Long quantity;
    private Long subtotal;
}
