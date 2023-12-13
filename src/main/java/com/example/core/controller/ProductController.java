package com.example.core.controller;

import com.example.core.dto.ProductSpecCharValueDTO;
import com.example.core.entity.ProductSpecCharValue;
import com.google.gson.GsonBuilder;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;
import com.example.core.dto.ProductSpecCharDTO;
import com.example.core.helper.ResponseObj;
import com.example.core.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RequestMapping("/api/product")
@RestController
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;

    @Transactional(rollbackOn = Exception.class)
    @ApiOperation(value = "create new product")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(path = "")
    @CrossOrigin
    public ResponseEntity create(@Nullable @RequestParam("id") Long id,
                                 @RequestParam("thumbnail") MultipartFile thumbnail,
                                 @RequestParam("images") MultipartFile[] images,
                                 @NotNull @RequestParam("productTypeId") Long productTypeId,
                                 @NotNull @RequestParam("name") String name,
                                 @NotNull @RequestParam("quantity") Long quantity,
                                 @NotNull @RequestParam("price") Long price,
                                 @NotNull @RequestParam("status") Boolean status,
                                 @Nullable @RequestParam("description") MultipartFile description,
                                 @RequestParam("productCharValues") String productCharValues) throws Exception {
       List<ProductSpecCharValueDTO> productCharValuesObj  = Arrays.asList(new GsonBuilder().create().fromJson(productCharValues, ProductSpecCharValueDTO[].class));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "create/update product successfully ", productService.create(id, thumbnail, Arrays.asList(images), productTypeId, name, quantity, price, status, description, productCharValuesObj)));
    }
    @ApiOperation(value = "get")
    @GetMapping(value = {"","/{id}"})
    @CrossOrigin
    public ResponseEntity get(HttpServletRequest request,@PathVariable(required = false) Long id) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "Get product type successfully", productService.get(ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString(), id)));

    }
}
