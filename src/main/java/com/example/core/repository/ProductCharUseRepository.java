package com.example.core.repository;

import com.example.core.entity.ProductSpecCharUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCharUseRepository extends JpaRepository<ProductSpecCharUse, Long> {
}
