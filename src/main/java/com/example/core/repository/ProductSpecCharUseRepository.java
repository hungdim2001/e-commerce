package com.example.core.repository;

import com.example.core.entity.ProductSpecCharUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductSpecCharUseRepository extends JpaRepository<ProductSpecCharUse, Long> {
    ProductSpecCharUse findByProductSpecCharValueID(Long charValueId);
    @Query(value = "select * from product_spec_char_uses where id in ?1",nativeQuery = true)
    List<ProductSpecCharUse> findByIds(List<Long> ids);

}
