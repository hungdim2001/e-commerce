package com.example.core.repository;

import com.example.core.dto.ProductSpecCharDTO;
import com.example.core.dto.ProductSpecCharValueDTO;
import com.example.core.entity.ProductSpecChar;
import com.example.core.entity.ProductSpecCharValue;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductCharRepositoryCustomImpl implements ProductCharRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List get(Long id) {

        StringBuilder sql = new StringBuilder();
        sql.append(
                " SELECT   psc.id AS psc_id,\n" +
                        "    psc.create_datetime AS psc_create_datetime,\n" +
                        "    psc.update_datetime AS psc_update_datetime,\n" +
                        "    psc.create_user AS psc_create_user,\n" +
                        "    psc.update_user AS psc_update_user,\n" +
                        "    psc.status AS psc_status,\n" +
                        "    psc.description AS psc_description,\n" +
                        "    psc.name AS psc_name,\n" +
                        "    pscv.id AS pscv_id,\n" +
                        "    pscv.create_datetime AS pscv_create_datetime,\n" +
                        "    pscv.update_datetime AS pscv_update_datetime,\n" +
                        "    pscv.create_user AS pscv_create_user,\n" +
                        "    pscv.update_user AS pscv_update_user,\n" +
                        "    pscv.status AS pscv_status,\n" +
                        "    pscv.description AS pscv_description,\n" +
                        "    pscv.value AS pscv_value\n" +
                        "FROM\n" +
                        "    product_spec_char_uses pscu\n" +
                        "        right   JOIN\n" +
                        "    product_spec_char_values pscv ON pscu.product_spec_char_valueid = pscv.id\n" +
                        "        right JOIN\n" +
                        "    product_spec_chars psc ON pscu.product_spec_charid = psc.id\n");
        if (id != null) {
            sql.append(" where psc.id = :id");
        }
        Query query = em.createNativeQuery(sql.toString());
        if (id != null) {
            query.setParameter("id", id);
        }
        List<Object[]> resultList = query.getResultList();
        List<Object[]> result = new ArrayList<>();

        for (Object[] row : resultList) {
            ProductSpecCharDTO productSpecCharDTO = ProductSpecCharDTO.builder()
                    .id(row[0] != null ? ((BigInteger) row[0]).longValue() : null)
                    .createDatetime(row[1] != null ? (Date) row[1] : null)
                    .updateDatetime(row[2] != null ? (Date) row[2] : null)
                    .createUser(row[3] != null ? ((BigInteger) row[3]).longValue() : null)
                    .updateUser(row[4] != null ? ((BigInteger) row[4]).longValue() : null)
                    .status(row[5] != null ? (Boolean) row[5] : null)
                    .description(row[6] != null ? (String) row[6] : null)
                    .name(row[7] != null ? (String) row[7] : null)
//                    .name(row[8] != null ? (String) row[8] : null)
                    .build();
            ProductSpecCharValueDTO productSpecCharValueDTO = new ProductSpecCharValueDTO();
            if (row[9] != null) {
                productSpecCharValueDTO = ProductSpecCharValueDTO.builder()
                        .id(((BigInteger) row[8]).longValue())
                        .createDatetime(row[9] != null ? (Date) row[10] : null)
                        .updateDatetime(row[10] != null ? (Date) row[11] : null)
                        .createUser(row[11] != null ? ((BigInteger) row[12]).longValue() : null)
                        .updateUser(row[12] != null ? ((BigInteger) row[13]).longValue() : null)
                        .status(row[13] != null ? (Boolean) row[14] : null)
                        .description(row[14] != null ? (String) row[15] : null)
                        .value(row[15] != null ? (String) row[16] : null)
//                        .value(row[17] != null ? (String) row[17] : null)
                        .build();

            }
            Object[] resultDTO = {productSpecCharDTO, productSpecCharValueDTO};
            result.add(resultDTO);

        }


        return result;
    }

    @Override
    @Transactional
    @Modifying
    public void delete(List<Long> ids) {
        String deletePscu = "DELETE FROM product_spec_char_uses WHERE product_spec_charid IN :ids";
        String deletePscv = "\n" +
                "DELETE pscv\n" +
                "FROM product_spec_char_values pscv\n" +
                "         JOIN product_spec_char_uses pscu ON pscv.id = pscu.product_spec_char_valueid\n" +
                "WHERE pscu.product_spec_charid IN :ids";
        String deletePsc = "DELETE FROM product_spec_chars WHERE id IN :ids";

        Query queryPscu = em.createNativeQuery(deletePscu).setParameter("ids", ids);
        Query queryPscv = em.createNativeQuery(deletePscv).setParameter("ids", ids);
        Query queryPsc = em.createNativeQuery(deletePsc).setParameter("ids", ids);

        queryPsc.executeUpdate();
        queryPscv.executeUpdate();
        queryPscu.executeUpdate();

    }


}

