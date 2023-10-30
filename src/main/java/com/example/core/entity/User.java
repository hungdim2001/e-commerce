package com.example.core.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@Table(name = "users")
@AllArgsConstructor
public class User extends AuditTable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "user_name", nullable = false)
    private String username;
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "AREA_CODE")
    private String areaCode;
    @Column(name="phone")
    private String phone;
}
