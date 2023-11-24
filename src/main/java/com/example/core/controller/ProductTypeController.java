package com.example.core.controller;

import com.example.core.helper.ResponseObj;
import com.example.core.service.ProductTypeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/product-type")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @Transactional(rollbackOn = Exception.class)
    @ApiOperation(value = "create")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(path = "", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @CrossOrigin
    public ResponseEntity create(@RequestParam("id")
                                 long id,
                                 @RequestParam("name")
                                 String name,
                                 @RequestParam("icon")
                                 MultipartFile icon) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "Create new product type successfully", productTypeService.create(id,name, icon)));

    }
}
