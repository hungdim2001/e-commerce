package com.example.core.dto;

import com.example.core.entity.AuditTable;
import com.example.core.entity.ProductSpecChar;

import javax.persistence.Column;
import java.util.List;

public class ProductDTO extends AuditTable {
    private Long productTypeId;
    private String name;
    private String quantity;
    private String thumbnail;
    private String price;
    private String code;
    private List<ProductSpecCharDTO> productSpecChars;
}
