package com.example.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@SuperBuilder
@Entity
@Data
@Table(name = "orders")
//@NoArgsConstructor
@AllArgsConstructor
public class Order extends AuditTable implements Serializable {
}
