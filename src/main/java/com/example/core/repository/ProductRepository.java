package com.example.core.repository;

import com.example.core.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,ProductRepositoryCustom {
}
