package com.example.core.dto;

import com.example.core.entity.AuditTable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;

@Data
@SuperBuilder
@NoArgsConstructor
public class AddressDTO extends AuditTable {
    private String areaCode;
    private String province;
    private String district;
    private String precinct;
    private String fullName;
    private String streetBlock;
    private String receiver;
    private String addressType;
    private String address;
    private String phone;
    private Boolean isDefault;
    private Long userId;
    private Double lat;
    private Double lon;
}
