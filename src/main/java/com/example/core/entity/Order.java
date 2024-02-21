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
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order extends AuditTable implements Serializable {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "shiping_method")
    private String shippingMethod;
    @Column(name = "ship_fee")
    private Long shippingFee;
    @Column(name="estimate_date")
    private Long estimateDate;
    @Column(name = "address_id")
    private Long addressId;
    @Column(name = "pay_method")
    private String payMethod;
    @Column(name="status_order")
    private String statusOrder;
}
