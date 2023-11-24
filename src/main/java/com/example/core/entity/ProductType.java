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
@Table(name = "product_types")
@NoArgsConstructor
@AllArgsConstructor
public class ProductType  extends AuditTable implements Serializable {
    @Column(name = "icon")
    private String icon;
    @Column(name = "NAME")
    private String name;
}
