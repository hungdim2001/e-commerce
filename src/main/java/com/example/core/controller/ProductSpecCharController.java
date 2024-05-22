package com.example.core.controller;


import com.example.core.dto.ProductSpecCharDTO;
import com.example.core.helper.ResponseObj;
import com.example.core.service.ProductSpecCharService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequestMapping("/api/product/char")
@RestController
@CrossOrigin
public class ProductSpecCharController {
    @Autowired
    ProductSpecCharService productSpecCharService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = {""})
    @ApiOperation(value = "Create new product spec char")
    @CrossOrigin
    public ResponseEntity create(@Valid @RequestBody ProductSpecCharDTO productSpecCharDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "create/update product spec char successfully ", productSpecCharService.create(productSpecCharDTO)));
    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PostMapping(value = {""})
//    @ApiOperation(value = "Update product spec char")
//    @CrossOrigin
//    public ResponseEntity update(@Valid @RequestBody ProductSpecCharDTO productSpecCharDTO) {
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "create product spec char successfully ", productSpecCharService.create(productSpecCharDTO)));
//    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = {""})
    @ApiOperation(value = "delete product spec char")
    @CrossOrigin
    public ResponseEntity delete(@RequestBody List<Long> productSpecCharIds) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "create product spec char successfully ", productSpecCharService.delete(productSpecCharIds)));
    }


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = {"","/{id}"})
    @ApiOperation(value = "get product spec char")
    public ResponseEntity get(@PathVariable(required = false) Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get product spec char successfully ", productSpecCharService.get(id)));
    }

}