package com.example.core.controller;

import com.example.core.entity.Address;
import com.example.core.helper.ResponseObj;
import com.example.core.service.AddressService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/address")
@RestController
@CrossOrigin
public class AddressController {
   @Autowired
    AddressService addressService;

    @PostMapping(value = {""})
    @ApiOperation(value = "create or update address")
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity createOrUpdate(@RequestBody Address address) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get area successfully", addressService.saveOrUpdate(address)));
    }


    @GetMapping(value = {"/{arg}"})
    @ApiOperation(value = "getAddress")
    @CrossOrigin
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity get(@PathVariable(required = false) Long arg) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get area successfully", addressService.getByUserId(arg)));
    }


}
