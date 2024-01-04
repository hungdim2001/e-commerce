package com.example.core.repository;

import com.example.core.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantRepository extends JpaRepository<Variant,Long> {
    List<Variant> findByProductId(Long id);
    void  deleteAllByProductId(Long id);
}
