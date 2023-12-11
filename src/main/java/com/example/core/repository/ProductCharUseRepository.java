package com.example.core.repository;

import com.example.core.entity.ProductCharUse;
import com.example.core.entity.ProductSpecCharUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCharUseRepository extends JpaRepository<ProductCharUse, Long> {
    List<ProductCharUse> findByProductId(Long id);

}
