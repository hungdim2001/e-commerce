package com.example.core.repository;

import com.example.core.dto.ProductSpecCharDTO;

import java.util.List;

public interface ProductCharRepositoryCustom {
    List get(Long id);

    void delete(List<Long> id);

}
