package com.example.core.dto;

import com.example.core.entity.AuditTable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class RatingDTO extends AuditTable {
    private String comment;
    private Long productId;
    private Long star;
    private String fullName;
    private Long userId;
}
