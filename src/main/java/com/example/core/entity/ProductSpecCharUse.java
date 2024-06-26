
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
@Table(name = "product_spec_char_uses")
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecCharUse extends AuditTable implements Serializable {
    @Column(name = "PRODUCT_SPEC_CHAR_VALUE_ID")
    private Long productSpecCharValueID;
    @Column(name = "PRODUCT_SPEC_CHAR_ID")
    private Long productSpecCharID;
}

