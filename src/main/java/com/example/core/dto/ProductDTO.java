package com.example.core.dto;

import com.example.core.entity.AuditTable;
import com.example.core.entity.ProductType;
import com.example.core.entity.Variant;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductDTO extends AuditTable {
    private ProductType productType;
    private Long productTypeId;
    private String name;
    private String quantity;
    private String thumbnail;
    private List<String> images;
    private Long sold;
    private Long price;
    private List<ProductSpecCharDTO> productSpecChars;
    private List<VariantDTO> variants;
    private List<RatingDTO> ratingDTOS;
}
