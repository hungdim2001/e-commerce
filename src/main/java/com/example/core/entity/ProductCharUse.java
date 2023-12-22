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
@Table(name = "product_char_uses")
@NoArgsConstructor
@AllArgsConstructor
public class ProductCharUse  extends AuditTable implements Serializable {
    @Column(name = "PRODUCT_ID")
    private Long productId;
    @Column(name = "PRODUCT_SPEC_CHAR_USE_ID")
    private Long productSpecCharUseId;
    @Column(name= "priority")
    private Long priority;


}
