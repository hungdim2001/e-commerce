package com.example.core.dto;

import lombok.Data;

@Data
public class CheckDuplicateCharValue {
    private Long productSpecCharId;
    private Long charValueId;
    private String value;
}
