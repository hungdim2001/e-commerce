package com.example.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tokens")
@Data
@SuperBuilder
@NoArgsConstructor
public class Token extends AuditTable implements Serializable {
    private String token;

}
