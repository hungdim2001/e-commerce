
package com.example.core.service;

import com.example.core.repository.ProductCharValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ProductSpecCharValueService {

    @Autowired
    ProductCharValueRepository productCharValueRepository;

    public boolean checkProductCharValueByCode(String code) {


        return productCharValueRepository.existsByCode(code);
    }
}
