package com.example.core.repository;

import com.example.core.entity.ProductSpecChar;
import com.example.core.entity.ProductSpecCharValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCharValueRepository extends JpaRepository<ProductSpecCharValue, Long>, ProductCharValueRepositoryCustom {
//    boolean existsByCode(String code);


    @Query(value = "select COUNT(*)  from product_spec_char_values where value = ?1 and (?2 IS NULL OR id != ?2)",nativeQuery = true)
    int existsByValueAndId(String value, Long id);
}