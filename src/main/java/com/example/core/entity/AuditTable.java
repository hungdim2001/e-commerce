package com.example.core.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;
@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class AuditTable {
    @Column(name = "create_datetime")
    private Date createDatetime;
    @Column(name = "update_datetime")
    private Date updateDatetime;
    @Column(name = "create_user")
    private Long createUser;
    @Column(name = "update_user")
    private Long updateUser;
    @Column(name = "status")
    private Boolean status;
}
