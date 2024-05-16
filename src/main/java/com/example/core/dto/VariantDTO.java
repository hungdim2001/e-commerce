package com.example.core.dto;

import com.example.core.entity.AuditTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class VariantDTO extends AuditTable {
    private Long productId;
    private List<Long> chars;
    private String image;
    private String name;
    private Long quantity;
    private Long price;
    private Long discountPrice;
    private Map<String,String> charValues;
}


