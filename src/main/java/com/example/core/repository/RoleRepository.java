package com.example.core.repository;

import com.example.core.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByRole(String role);

    Role getRoleByRole(String role);

}
