package com.example.core.entity;

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
@Table(name = "cart_items")
@NoArgsConstructor
public class CartItem extends AuditTable implements Serializable {
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "VARIANT_ID")
    private Long variantId;
    @Column(name = "QUANTITY")
    private Long quantity;
}
