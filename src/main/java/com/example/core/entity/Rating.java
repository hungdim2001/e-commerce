package com.example.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ratings")
@SuperBuilder
@Data
@NoArgsConstructor
public class Rating extends AuditTable implements Serializable {
    @Column(name = "comment", nullable = false)
    private String comment;
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Column(name = "star", nullable = false)
    private Long star;
    @Column(name = "user_Id", nullable = false)
    private Long userId;
}
