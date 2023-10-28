package com.example.core.repository;

import com.example.core.entity.Area;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class AreaRepositoryCustomImpl implements AreaRepositoryCustom {
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Area> getArea(String parentCode) {
        String queryStr = "SELECT a.* FROM areas a where a.status = true";
        if (parentCode !=null) {
            queryStr += " and a.parent_code  = :parentCode";
        } else {
            queryStr += " and a.parent_code is null";
        }
        Query query = em.createNativeQuery(queryStr, Area.class);
        if (parentCode !=null) {
            query.setParameter("parentCode", parentCode);
        }
        return query.getResultList();

    }

}
