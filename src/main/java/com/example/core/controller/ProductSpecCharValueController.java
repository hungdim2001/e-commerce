package com.example.core.controller;

import com.example.core.dto.CheckDuplicateCharValue;
import com.example.core.dto.ProductSpecCharDTO;
import com.example.core.helper.ResponseObj;
import com.example.core.service.ProductSpecCharService;
import com.example.core.service.ProductSpecCharValueService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/product/char/value")
@RestController
@CrossOrigin
public class ProductSpecCharValueController {
    @Autowired
    ProductSpecCharValueService productSpecCharValueService;

    @PostMapping(value = {""})
    @ApiOperation(value = "check duplicate  spec char value code")
    @CrossOrigin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity check(@RequestBody CheckDuplicateCharValue object) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "check duplicate  spec char value code successfully", productSpecCharValueService.checkProductCharValueByCodeAndID(object.getValue(),object.getProductSpecCharId(), object.getCharValueId())));

    }
    @DeleteMapping(value = {""})
    @ApiOperation(value = "delete product spec char value")
    @CrossOrigin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity delete(@RequestBody List<Long> ids) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "Create new product spec char successfully", productSpecCharValueService.delete(ids)));

    }
}
