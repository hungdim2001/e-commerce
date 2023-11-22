package com.example.core.repository;

import com.example.core.entity.ProductSpecChar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCharRepository extends JpaRepository<ProductSpecChar, Long>,ProductCharRepositoryCustom {
//    boolean existsByCode(String code);
    @Query(value = "select count(*) from product_spec_chars where name = ?1 and (?2 IS NULL OR id != ?2)",nativeQuery = true)
    int existsByNameAndId(String code, Long id);
}
