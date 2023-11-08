
package com.example.core.controller;


import com.example.core.dto.ProductSpecCharDTO;
import com.example.core.helper.ResponseObj;
import com.example.core.service.ProductSpecCharService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/product/char")
@RestController
@CrossOrigin
public class ProductSpecCharController {
    @Autowired
    ProductSpecCharService productSpecCharService;

    @PostMapping(value = {""})
    @ApiOperation(value = "Create new product spec char")
    @CrossOrigin
    public ResponseEntity create(@Valid @RequestBody ProductSpecCharDTO productSpecCharDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "create product spec char successfully ", productSpecCharService.create(productSpecCharDTO)));
    }
    @PostMapping(value = {""})
    @ApiOperation(value = "get product spec char")
    @CrossOrigin
    public ResponseEntity get() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get product spec char successfully ", productSpecCharService.get()));
    }

}