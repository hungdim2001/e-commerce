
package com.example.core.dto;

import com.example.core.entity.AuditTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class ProductSpecCharDTO extends AuditTable {
    private String code;
    private String name;
    private ProductSpecCharValueDTO[] productSpecCharValueDTOS;
}
