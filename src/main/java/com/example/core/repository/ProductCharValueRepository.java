package com.example.core.repository;

import com.example.core.entity.ProductSpecChar;
import com.example.core.entity.ProductSpecCharValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCharValueRepository extends JpaRepository<ProductSpecCharValue, Long>,ProductCharValueRepositoryCustom {
    boolean existsByCode(String Code);
}