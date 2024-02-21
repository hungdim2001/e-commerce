package com.example.core.dto.request;

import lombok.Data;

@Data
public class OrderRequest {
    private String shippingMethod;
    private Long shippingFee;
    private Long estimateDate;
    private Long addressId;
}
