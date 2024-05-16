package com.example.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@Table(name = "variants")
@AllArgsConstructor
public class Variant extends AuditTable implements Serializable {
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "chars", nullable = false)
    private String chars;
    @Column(name = "image", nullable = false)
    private String image;
    @Column(name = "quantity")
    private Long quantity;
    @Column(name = "price")
    private Long price;
    @Column(name = "discount_price")
    private Long discountPrice;
}

