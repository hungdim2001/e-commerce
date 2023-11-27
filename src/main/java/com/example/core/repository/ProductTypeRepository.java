package com.example.core.repository;

import com.example.core.entity.ProductType;
import com.example.core.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    boolean findByName(String name);

    @Query(value = "select count(*) from product_types where name = ?1 and (?2 IS NULL OR id != ?2)",nativeQuery = true)
    int existsByNameAndId(String code, Long id);
    @Query(value = "select * from product_types where id in ?1", nativeQuery = true)
    List<ProductType> findByIds(List<Long> ids);
    @Query(value = "delete from product_types where id in ?1", nativeQuery = true)
    void delete(List<Long> ids);



}
