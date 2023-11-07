package com.example.core.dto;

import com.example.core.entity.AuditTable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
@Data
@SuperBuilder
@NoArgsConstructor
public class AreaDTO   extends AuditTable {
    private String areaCode;
    private String province;
    private String district;
    private String precinct;
    private String streetBlock;
    private String parentCode;
    private String name;
    private String fullName;
//    private String provinceName;
//    private String districtName;
//    private String precinctName;
//    private String streetBlockName;
}
