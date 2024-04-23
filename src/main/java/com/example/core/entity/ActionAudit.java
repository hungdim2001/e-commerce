package com.example.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@SuperBuilder
@Entity
@Data
@Table(name = "ACTION_AUDIT")
@NoArgsConstructor
@AllArgsConstructor
public class ActionAudit extends AuditTable implements Serializable {
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name="IP_CLIENT")
    private String ipClient;
    @Column(name = "ACTION_TIME")
    private Date actionTime;
    @Column(name="ACTION")
    private String action;
    @Column(name = "PRODUCT_ID")
    private Long productId;
    @Column(name= "KEYWORD")
    private String keyword;
    @Column(name= "LAT")
    private String lat;
    @Column(name= "LON")
    private String lon;
    @Column(name= "ROAD")
    private String road;
    @Column(name= "QUARTER")
    private String quarter;
    @Column(name= "SUBURB")
    private String suburb;
    @Column(name= "CITY")
    private String city;
    @Column(name= "POSTCODE")
    private String postcode;
    @Column(name= "COUNTRY")
    private String country;
    @Column(name= "COUNTRY_CODE")
    private String country_code;
    @Column(name="USER_AGENT")
    private String userAgent;
}
