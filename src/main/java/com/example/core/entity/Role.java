package com.example.core.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "roles")
@SuperBuilder
@Data
@NoArgsConstructor
public class Role extends AuditTable implements Serializable {

    private String role;
    /*
    * INSERT INTO roles (role)
VALUES ('ADMIN');
    *
    * */
}
