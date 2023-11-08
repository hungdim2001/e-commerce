package com.example.core.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;
@Data
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class AuditTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @Column(name = "description")
    protected String description;
}
