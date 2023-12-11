package com.example.core.repository;

import com.example.core.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>,ProductRepositoryCustom {
    @Query(value = "select * from products  where id = ?1 or ?1 is null ", nativeQuery = true)
    List<Product> findByIdCus(Long id);
}
