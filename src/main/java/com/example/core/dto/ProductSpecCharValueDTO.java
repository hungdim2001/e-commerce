

package com.example.core.dto;

import com.example.core.entity.AuditTable;
import com.example.core.entity.ProductSpecCharValue;
import com.example.core.exceptions.DuplicateException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import javax.persistence.Column;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class ProductSpecCharValueDTO   extends AuditTable {
    private String code;
    private String value;
    public static void checkForDuplicates(List<ProductSpecCharValue> productSpecCharValueDTOs) {
        Map<String, ProductSpecCharValue> codeMap = new HashMap<>();
        Map<Long, ProductSpecCharValue> idMap = new HashMap<>();

        for (ProductSpecCharValue productSpecCharValue : productSpecCharValueDTOs) {
            String code = productSpecCharValue.getCode();
            Long id = productSpecCharValue.getId();

            if (codeMap.containsKey(code)) {
                throw new DuplicateException(HttpStatus.CONFLICT, "Duplicate code value: " + code);
            }

            if (idMap.containsKey(id)) {
                throw new DuplicateException(HttpStatus.CONFLICT,"Duplicate id value: " + id);
            }

            codeMap.put(code, productSpecCharValue);
            idMap.put(id, productSpecCharValue);
        }
    }
}
