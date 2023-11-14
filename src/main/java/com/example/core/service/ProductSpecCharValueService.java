
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

    public boolean checkProductCharValueByCode(String code) {


        return productCharValueRepository.existsByCode(code);
    }

    public List<ProductSpecCharDTO> delete(List<Long> ids) {
        productCharValueRepository.delete(ids);
        return productSpecCharService.get(null);
    }
}
