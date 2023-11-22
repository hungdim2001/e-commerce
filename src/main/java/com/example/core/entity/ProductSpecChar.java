
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
@Table(name = "product_spec_chars")
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecChar extends AuditTable implements Serializable {

//    @Column(name = "CODE")
//    private String code;
    @Column(name = "NAME")
    private String name;
}
