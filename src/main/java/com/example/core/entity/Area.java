package com.example.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@SuperBuilder
@Entity
@Data
@Table(name = "areas")
@NoArgsConstructor
@AllArgsConstructor
public class Area extends AuditTable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "AREA_CODE")
    private String areaCode;
    @Column(name = "PROVINCE")
    private String province;
    @Column(name = "DISTRICT")
    private String district;
    @Column(name = "PRECINCT")
    private String precinct;
    @Column(name = "STREET_BLOCK")
    private String streetBlock;
    @Column(name = "PARENT_CODE")
    private String parentCode;
    @Column(name = "NAME")
    private String name;
    @Column(name = "FULL_NAME")
    private String fullName;
}
