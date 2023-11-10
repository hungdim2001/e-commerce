package com.example.core.repository;

import com.example.core.entity.ProductSpecChar;
import com.example.core.entity.ProductSpecCharValue;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class ProductCharRepositoryCustomImpl implements  ProductCharRepositoryCustom{
    @PersistenceContext
    private EntityManager em;
    @Override
    public List getFull() {

        StringBuilder sql = new StringBuilder();
        sql.append("select psc.*, pscv.*\n" +
                "from product_spec_char_uses pscu,\n" +
                "              product_spec_char_values pscv,\n" +
                "              product_spec_chars psc\n" +
                "where pscu.product_spec_char_valueid = pscv.id\n" +
                "and pscu.product_spec_charid = psc.id");

        Query query = em.createNativeQuery(sql.toString() );

//        query.unwrap(SQLQuery.class).addEntity(ProductSpecChar.class).addEntity(ProductSpecCharValue.class);

        List<Object[]> resultList = query.getResultList();


        return resultList;
    }
}

