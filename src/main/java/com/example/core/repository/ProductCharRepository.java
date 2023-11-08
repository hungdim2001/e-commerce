package com.example.core.repository;

import com.example.core.entity.ProductSpecChar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCharRepository extends JpaRepository<ProductSpecChar, Long>,ProductCharRepositoryCustom {
}
