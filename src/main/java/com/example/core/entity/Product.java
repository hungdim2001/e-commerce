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
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AuditTable implements Serializable {

    @Column(name = "PRODUCT_TYPE_ID")
    private Long productTypeId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "QUANTITY")
    private String quantity;
    @Column(name = "THUMBNAIL")
    private String thumbnail;
    @Column(name = "PRICE")
    private String price;
    @Column(name = "CODE")
    private String code;
}
