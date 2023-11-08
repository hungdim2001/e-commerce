package com.example.core.repository;

import com.example.core.dto.ProductSpecCharDTO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ProductCharRepositoryCustomImpl implements  ProductCharRepositoryCustom{
    @PersistenceContext
    private EntityManager em;
    @Override
    public ProductSpecCharDTO getFull() {
        StringBuilder sql = new StringBuilder();
//        sql.append("")
        return null;
    }
}

