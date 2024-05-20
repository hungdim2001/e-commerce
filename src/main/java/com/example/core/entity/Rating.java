package com.example.core.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "ratings")
@SuperBuilder
@Data
@NoArgsConstructor
public class Rating extends AuditTable implements Serializable {
    private String comment;
    private Long productId;
    private Long star;
    private Long userId;
}
