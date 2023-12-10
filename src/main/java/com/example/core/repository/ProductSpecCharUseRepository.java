package com.example.core.repository;

import com.example.core.entity.ProductSpecCharUse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecCharUseRepository extends JpaRepository<ProductSpecCharUse, Long> {
    ProductSpecCharUse findByProductSpecCharValueID(Long charValueId);

}
