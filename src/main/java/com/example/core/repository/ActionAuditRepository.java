package com.example.core.repository;

import com.example.core.entity.ActionAudit;
import com.example.core.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionAuditRepository extends JpaRepository<ActionAudit, Long> {
}
