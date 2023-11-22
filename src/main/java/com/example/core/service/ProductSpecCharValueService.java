
package com.example.core.service;

import com.example.core.dto.ProductSpecCharDTO;
import com.example.core.repository.ProductCharValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProductSpecCharValueService {

    @Autowired
    ProductCharValueRepository productCharValueRepository;
    @Autowired
    ProductSpecCharService productSpecCharService;

    public boolean checkProductCharValueByCodeAndID(String code,Long id) {


        return productCharValueRepository.existsByValueAndId(code,id)>0;
    }

    public List<ProductSpecCharDTO> delete(List<Long> ids) {
        productCharValueRepository.delete(ids);
        return productSpecCharService.get(null);
    }
}
