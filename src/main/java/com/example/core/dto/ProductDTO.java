package com.example.core.dto;

import com.example.core.entity.AuditTable;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
public class ProductDTO extends AuditTable {
    private Long productTypeId;
    private String name;
    private String quantity;
    private String thumbnail;
    private List<String> images;
    private Long price;
    private List<ProductSpecCharDTO> productSpecChars;
}
