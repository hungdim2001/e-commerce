package com.example.core.controller;

import com.example.core.helper.ResponseObj;
import com.example.core.service.ProductTypeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @RequestMapping(path = "", method = POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @CrossOrigin
    public ResponseEntity create(@Nullable @RequestParam("id") Long id, @Nullable @RequestParam("name") String name, @Nullable @RequestParam("description") String description, @Nullable @RequestParam("status") Boolean status, @Nullable @RequestParam("icon") MultipartFile icon) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "Create/Update product type successfully",  productTypeService.create(id, name, description, status, icon)));

    }

    @Transactional(rollbackOn = Exception.class)
    @ApiOperation(value = "delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(path = "")
    @CrossOrigin
    public ResponseEntity delete(@RequestBody List<Long> ids) throws Exception {
        productTypeService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "Delete product type successfully", null));

    }


    @ApiOperation(value = "get")
    @GetMapping("")
    @CrossOrigin
    public ResponseEntity get(HttpServletRequest request) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "Get product type successfully", productTypeService.get(ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString())));

    }
}
