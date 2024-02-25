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
@Table(name = "order_details")
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail extends AuditTable implements Serializable {
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "variant_id")
    private Long variantId;
    @Column(name = "order_price")
    private Long orderPrice;
    @Column(name = "quantity")
    private Long quantity;
}
