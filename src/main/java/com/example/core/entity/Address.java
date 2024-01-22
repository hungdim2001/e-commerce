package com.example.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@SuperBuilder
@Entity
@Data
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
public class Address extends AuditTable implements Serializable {
    @Column(name = "AREA_CODE")
    private String areaCode;
    @Column(name = "RECEIVER")
    private String receiver;
    @Column(name = "ADDRESS_TYPE")
    private String addressType;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "IS_DEFAULT")
    private Boolean isDefault;
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "LAT")
    private Double lat;
    @Column(name = "LON")
    private Double lon;
}
