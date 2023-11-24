package com.example.core.repository;

import com.example.core.entity.ProductType;
import com.example.core.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    boolean findByName(String name);

    @Query(value = "select count(*) from product_types where name = ?1 and (?2 IS NULL OR id != ?2)",nativeQuery = true)
    int existsByNameAndId(String code, Long id);


}
