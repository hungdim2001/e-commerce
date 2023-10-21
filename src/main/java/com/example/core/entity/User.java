package com.example.core.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Data
@SuperBuilder
@Table(name = "users")
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
    @Column(name = "status", nullable = false)
    private Boolean status;
    public User() {
        super();
    }
}
