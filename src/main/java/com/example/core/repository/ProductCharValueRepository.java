package com.example.core.repository;

import com.example.core.entity.ProductSpecChar;
import com.example.core.entity.ProductSpecCharValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCharValueRepository extends JpaRepository<ProductSpecCharValue, Long>, ProductCharValueRepositoryCustom {
//    boolean existsByCode(String code);


    @Query(value = "SELECT count(*)\n" +
            "       FROM product_spec_char_values pscv,\n" +
            "     product_spec_chars psc,\n" +
            "     product_spec_char_uses pscu\n" +
            "where pscv.id = pscu.product_spec_char_value_id\n" +
            "  and pscu.product_spec_char_id = psc.id\n" +
            "  and( ?2 is not null and psc.id = ?2\n" +
            "  and pscv.value = ?1) and (?3 is not null and  pscv.id !=  ?3)",nativeQuery = true)
    int existsByValueAndId(String value, Long charId, Long charValueId);
}