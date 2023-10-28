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
    protected Date createDatetime;
    @Column(name = "update_datetime")
    protected Date updateDatetime;
    @Column(name = "create_user")
    protected Long createUser;
    @Column(name = "update_user")
    protected Long updateUser;
    @Column(name = "status")
    protected Boolean status;
}
