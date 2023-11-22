
package com.example.core.service;

import com.example.core.dto.ProductSpecCharDTO;
import com.example.core.dto.ProductSpecCharValueDTO;
import com.example.core.entity.AuditTable;
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

import java.util.*;
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
        List<ProductSpecCharValue> productSpecCharValues = productSpecCharDTO.getProductSpecCharValueDTOS().stream().
                map(productSpecCharValueDTO -> {
                    if (productSpecCharValueDTO.getCreateDatetime() == null) {
                        productSpecCharValueDTO.setCreateDatetime(new Date());
                    }
                    return modelMapper.map(productSpecCharValueDTO, ProductSpecCharValue.class);
                }).collect(Collectors.toList());
        //check duplicate code product spec char
        if (productCharRepository.existsByNameAndId(productSpecCharDTO.getName(), productSpecCharDTO.getId()) > 0) {
            throw new DuplicateException(HttpStatus.CONFLICT, "Duplicate product spec char code value: " + productSpecCharDTO.getName());
        }
//check duplicate code product spec char value
        checkDuplicateValue(productSpecCharValues);
        List<Long> oldProductCharValueId = productSpecCharValues.stream().filter(item -> item.getId() != null).map(item ->
                item.getId()).collect(Collectors.toList());
//save map from input productSpecCharDTO to ProductSpecChar
        ProductSpecChar productSpecChar = modelMapper.map(productSpecCharDTO, ProductSpecChar.class);
//        if (productSpecChar.getCreateDatetime() == null) {
//            productSpecChar.setCreateDatetime(new Date());
//        }
        //save ProductCharValue
        List<ProductSpecCharValueDTO> productSpecCharValueDTOS = productCharValueRepository.
                saveAll(productSpecCharValues).stream().map(productSpecCharValue -> modelMapper.
                        map(productSpecCharValue, ProductSpecCharValueDTO.class)).collect(Collectors.toList());
        //Save ProductChar
        ProductSpecCharDTO result = modelMapper.map(productCharRepository.save(productSpecChar), ProductSpecCharDTO.class);


        List<ProductSpecCharUse> productSpecCharUses = productSpecCharValueDTOS.stream().filter(item -> !oldProductCharValueId.contains(item.getId())).
                map(productSpecCharValueDTO -> ProductSpecCharUse.builder().productSpecCharID(result.getId()).
                        productSpecCharValueID(productSpecCharValueDTO.getId()).status(true).
                        createDatetime(new Date()).createUser(productSpecCharValueDTO.getCreateUser()).build()).collect(Collectors.toList());
        productCharUseRepository.saveAll(productSpecCharUses);
        result.setProductSpecCharValueDTOS(productSpecCharValueDTOS);
        return result;
    }


    public ProductSpecCharDTO update(ProductSpecCharDTO productSpecCharDTO) {

        List<ProductSpecCharValue> productSpecCharValues = productSpecCharDTO.getProductSpecCharValueDTOS().stream().
                map(productSpecCharValueDTO -> {
                    if (productSpecCharValueDTO.getCreateDatetime() == null) {
                        productSpecCharValueDTO.setCreateDatetime(new Date());
                    }
                    return modelMapper.map(productSpecCharValueDTO, ProductSpecCharValue.class);
                }).collect(Collectors.toList());
//check duplicate code product spec char
        if (productCharRepository.existsByNameAndId(productSpecCharDTO.getName(), productSpecCharDTO.getId()) > 0) {
            throw new DuplicateException(HttpStatus.CONFLICT, "Duplicate product spec char code value: " + productSpecCharDTO.getName());
        }

//check duplicate Value product spec char value
        checkDuplicateValue(productSpecCharValues);
//save map from input productSpecCharDTO to ProductSpecChar
        ProductSpecChar productSpecChar = modelMapper.map(productSpecCharDTO, ProductSpecChar.class);
//        if (productSpecChar.getCreateDatetime() == null) {
//            productSpecChar.setCreateDatetime(new Date());
//        }
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
        result.setProductSpecCharValueDTOS(productSpecCharValueDTOS);
        return result;
    }

    public List<ProductSpecCharDTO> get(Long id) {
        List<Object[]> resultObj = productCharRepository.get(id);


        List<ProductSpecCharDTO> result = new ArrayList<>();
        if (!resultObj.isEmpty()) {
            ProductSpecCharDTO tmp = modelMapper.map(resultObj.get(0)[0], ProductSpecCharDTO.class);
            List<ProductSpecCharValueDTO> tmpValue = new ArrayList<>();
            for (Object[] item : resultObj) {
                ProductSpecCharDTO productSpecCharDTO = modelMapper.map(item[0], ProductSpecCharDTO.class);
                ProductSpecCharValueDTO specCharValueDTO = modelMapper.map(item[1], ProductSpecCharValueDTO.class);
                if (tmp.getId() != productSpecCharDTO.getId()) {
                    tmp.setProductSpecCharValueDTOS(tmpValue);
                    result.add(tmp);
                    tmp = modelMapper.map(item[0], ProductSpecCharDTO.class);
                    tmpValue = new ArrayList<>();
                    tmpValue.add(specCharValueDTO);
                } else {
                    tmpValue.add(specCharValueDTO);
                }

            }
            tmp.setProductSpecCharValueDTOS(tmpValue);
            result.add(tmp);


        }
        return result;
    }

    public List<ProductSpecCharDTO> delete(List<Long> productSpecCharIds) {
        productCharRepository.delete(productSpecCharIds);
        return get(null);

    }

    public boolean checkDuplicateValue(List<ProductSpecCharValue> productSpecCharValues) {
        // Create a HashMap to store encountered codes
        HashMap<String, ProductSpecCharValue> codeToProductSpecCharValue = new HashMap<>();

        for (ProductSpecCharValue productSpecCharValue : productSpecCharValues) {
            // Check if the Value already exists in the HashMap
            if (codeToProductSpecCharValue.containsKey(productSpecCharValue.getValue())) {
                throw new DuplicateException(HttpStatus.CONFLICT, "Duplicate code product spec value: " + productSpecCharValue.getValue());
            }

            // Check if the Value already exists in the database
            if (productCharValueRepository.existsByValueAndId(productSpecCharValue.getValue(), productSpecCharValue.getId()) > 0) {
                throw new DuplicateException(HttpStatus.CONFLICT, "Duplicate code product spec value: " + productSpecCharValue.getValue());
            }

            // Add the code to the HashMap
            codeToProductSpecCharValue.put(productSpecCharValue.getValue(), productSpecCharValue);
        }

        return true;
    }

//    public boolean checkDuplicateCodeUpdate(List<ProductSpecCharValue> productSpecCharValues) {
//        // Create a HashMap to store encountered codes
//        HashMap<String, ProductSpecCharValue> codeToProductSpecCharValue = new HashMap<>();
//
//        for (ProductSpecCharValue productSpecCharValue : productSpecCharValues) {
//            // Check if the code already exists in the HashMap
//            if (codeToProductSpecCharValue.containsKey(productSpecCharValue.getValue())) {
//                throw new DuplicateException(HttpStatus.CONFLICT, "Duplicate code value: " + productSpecCharValue.getValue());
//            }
//
//
//            if (productCharValueRepository.existsByValueAndId(productSpecCharValue.getValue(), productSpecCharValue.getId()) > 0) {
//                throw new DuplicateException(HttpStatus.CONFLICT, "Duplicate code value: " + productSpecCharValue.getValue());
//            }
//
//            // Add the code to the HashMap
//            codeToProductSpecCharValue.put(productSpecCharValue.getValue(), productSpecCharValue);
//        }
//
//        return true;
//    }


}
