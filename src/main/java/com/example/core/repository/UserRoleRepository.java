package com.example.core.repository;

import com.example.core.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRoleRepository  extends JpaRepository<UserRole,Long> {
    @Query(value = "SELECT u FROM UserRole u WHERE u.userId= ?1")
    Optional<UserRole> findByUserId(Long aLong);
}
