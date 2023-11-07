package com.example.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@SuperBuilder
@Entity
@Data
@Table(name = "codes")
@NoArgsConstructor
@AllArgsConstructor
public class Code  extends AuditTable implements Serializable {

    private String code;
    @Column(name = "user_id")
    private Long userId;
    @Column(name="expire_time")
    private Long expiredTime;



}
