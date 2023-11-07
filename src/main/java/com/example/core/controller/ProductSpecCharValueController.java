package com.example.core.controller;

import com.example.core.dto.ProductSpecCharDTO;
import com.example.core.helper.ResponseObj;
import com.example.core.service.ProductSpecCharService;
import com.example.core.service.ProductSpecCharValueService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/product/char/value")
@RestController
@CrossOrigin
public class ProductSpecCharValueController {
    @Autowired
    ProductSpecCharValueService productSpecCharValueService;

    @PostMapping(value = {"/{code}"})
    @ApiOperation(value = "Create new product spec char")
    @CrossOrigin
    public ResponseEntity create(@PathVariable(required = true) String code) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "Create new product spec char successfully", productSpecCharValueService.checkProductCharValueByCode(code)));

    }
}
