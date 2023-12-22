

package com.example.core.dto;

import com.example.core.entity.AuditTable;
import com.example.core.entity.ProductSpecCharValue;
import com.example.core.exceptions.DuplicateException;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import javax.persistence.Column;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@SuperBuilder
@NoArgsConstructor
public class ProductSpecCharValueDTO   extends AuditTable {
    private String value;
    private Long priority;

}
