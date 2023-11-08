
package com.example.core.service;

import com.example.core.dto.ProductSpecCharDTO;
import com.example.core.dto.ProductSpecCharValueDTO;
import com.example.core.entity.ProductSpecChar;
import com.example.core.entity.ProductSpecCharUse;
import com.example.core.entity.ProductSpecCharValue;
import com.example.core.exceptions.DuplicateException;
import com.example.core.repository.ProductCharRepository;
import com.example.core.repository.ProductCharUseRepository;
import com.example.core.repository.ProductCharValueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSpecCharService {
    @Autowired
    ProductCharRepository productCharRepository;
    @Autowired
    ProductCharValueRepository productCharValueRepository;
    @Autowired
    ProductCharUseRepository productCharUseRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ProductSpecCharDTO create(ProductSpecCharDTO productSpecCharDTO) {

        List<ProductSpecCharValue> productSpecCharValues = Arrays.stream(productSpecCharDTO.getProductSpecCharValueDTOS()).
                map(productSpecCharValueDTO -> {
                    if (productSpecCharValueDTO.getCreateDatetime() == null) {
                        productSpecCharValueDTO.setCreateDatetime(new Date());
                    }
                    return modelMapper.map(productSpecCharValueDTO, ProductSpecCharValue.class);
                }).collect(Collectors.toList());
//check duplicate code product spec char value
        checkDuplicateCode(productSpecCharValues);
//save map from input productSpecCharDTO to ProductSpecChar
        ProductSpecChar productSpecChar = modelMapper.map(productSpecCharDTO, ProductSpecChar.class);
        if (productSpecChar.getCreateDatetime() == null) {
            productSpecChar.setCreateDatetime(new Date());
        }
        //save ProductCharValue
        List<ProductSpecCharValueDTO> productSpecCharValueDTOS = productCharValueRepository.
                saveAll(productSpecCharValues).stream().map(productSpecCharValue -> modelMapper.
                        map(productSpecCharValue, ProductSpecCharValueDTO.class)).collect(Collectors.toList());
        //Save ProductChar
        ProductSpecCharDTO result = modelMapper.map(productCharRepository.save(productSpecChar), ProductSpecCharDTO.class);


        List<ProductSpecCharUse> productSpecCharUses = productSpecCharValueDTOS.stream().
                map(productSpecCharValueDTO -> ProductSpecCharUse.builder().productSpecCharID(result.getId()).
                        productSpecCharValueID(productSpecCharValueDTO.getId()).status(true).
                        createDatetime(new Date()).createUser(productSpecCharValueDTO.getCreateUser()).build()).collect(Collectors.toList());
        productCharUseRepository.saveAll(productSpecCharUses);
        result.setProductSpecCharValueDTOS(productSpecCharValueDTOS.toArray(new ProductSpecCharValueDTO[0]));
        return result;
    }

    public ProductSpecCharDTO get(){
        return null;
    }
    public boolean checkDuplicateCode(List<ProductSpecCharValue> productSpecCharValues) {
        // Create a HashMap to store encountered codes
        HashMap<String, ProductSpecCharValue> codeToProductSpecCharValue = new HashMap<>();

        for (ProductSpecCharValue productSpecCharValue : productSpecCharValues) {
            // Check if the code already exists in the HashMap
            if (codeToProductSpecCharValue.containsKey(productSpecCharValue.getCode())) {
                throw new DuplicateException(HttpStatus.CONFLICT, "Duplicate code value: " + productSpecCharValue.getCode());
            }

            // Check if the code already exists in the database
            if (productCharValueRepository.existsByCode(productSpecCharValue.getCode())) {
                throw new DuplicateException(HttpStatus.CONFLICT, "Duplicate code value: " + productSpecCharValue.getCode());
            }

            // Add the code to the HashMap
            codeToProductSpecCharValue.put(productSpecCharValue.getCode(), productSpecCharValue);
        }

        return true;
    }
}
