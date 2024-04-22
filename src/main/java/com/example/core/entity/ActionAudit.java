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
    @Column(name = "BROWSER")
    private String browser;
    @Column(name="IP_CLIENT")
    private String ipClient;
    @Column(name = "ACTION_TIME")
    private Date actionTime;
    @Column(name="ACTION")
    private String action;
    @Column(name = "PRODUCT_ID")
    private Long productId;
    @Column(name="DEVICE_TYPE")
    private String deviceType;
    @Column(name = "location")
    private String location;
    @Column(name= "KEYWORD")
    private String search;
}
