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
@Table(name = "product_images")
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage extends AuditTable implements Serializable {

    @Column(name ="PRODUCT_ID")
    private Long productId;
    @Column(name ="IMAGE")
    private String image;
}
