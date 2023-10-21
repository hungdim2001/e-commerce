package com.example.core.service;

import com.example.core.entity.Role;
import com.example.core.exceptions.DuplicateException;
import com.example.core.repository.RoleRepository;
import com.example.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(String role) {
        if (roleRepository.existsByRole(role)) {
            throw new DuplicateException(HttpStatus.CONFLICT, "role is duplicate");
        }
        return roleRepository.save(Role.builder().role(role).createDatetime(new Date()).build());
    }
}
