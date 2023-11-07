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
@Table(name = "product_specifications")
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecification  extends AuditTable implements Serializable {

    @Column(name = "NAME")
    private String name;

}
