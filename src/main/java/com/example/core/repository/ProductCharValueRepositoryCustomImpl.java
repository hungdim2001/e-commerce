package com.example.core.repository;

import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

public class ProductCharValueRepositoryCustomImpl implements  ProductCharValueRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    @Modifying
    public void delete(List<Long> ids) {
        String deletePscu = "DELETE\n" +
                "FROM product_spec_char_uses\n" +
                "WHERE product_spec_char_value_id IN :ids";
        String deletePscv = "DELETE pscv\n" +
                "FROM product_spec_char_values pscv\n" +
                "         JOIN product_spec_char_uses pscu ON pscv.id = pscu.product_spec_char_value_id\n" +
                "WHERE pscu.product_spec_char_value_id IN :ids";

        Query queryPscu = em.createNativeQuery(deletePscu).setParameter("ids", ids);
        Query queryPscv = em.createNativeQuery(deletePscv).setParameter("ids", ids);
        queryPscv.executeUpdate();
        queryPscu.executeUpdate();
    }
}
