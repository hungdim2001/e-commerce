package com.example.core.controller;

import com.example.core.helper.ResponseObj;
import com.example.core.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
                                 @Nullable @RequestParam("thumbnail") MultipartFile thumbnail,
                                 @Nullable @RequestParam("images") MultipartFile[] images,
                                 @Nullable @RequestParam("oldImages") String[] oldImages,
                                 @NotNull @RequestParam("productTypeId") Long productTypeId,
                                 @NotNull @RequestParam("name") String name,
                                 @NotNull @RequestParam("quantity") Long quantity,
                                 @NotNull @RequestParam("price") Long price,
                                 @NotNull @RequestParam("status") Boolean status,
                                 @Nullable @RequestParam("description") MultipartFile description,
                                 @Nullable  @RequestParam("productCharValues") String[] productCharValues,
                                 @Nullable @RequestParam("priority") String[]  priority) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "create/update product successfully ", productService.create(id, thumbnail, images, oldImages, productTypeId, name, quantity, price, status, description, Arrays.asList(productCharValues),Arrays.asList(priority))));
    }

    @ApiOperation(value = "get")
    @GetMapping(value = {"", "/{id}"})
    @CrossOrigin
    public ResponseEntity get(HttpServletRequest request, @PathVariable(required = false) Long id) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "Get product successfully", productService.get(ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString(), id)));

    }

    @Transactional(rollbackOn = Exception.class)
    @ApiOperation(value = "delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(path = "")
    @CrossOrigin
    public ResponseEntity delete(@RequestBody List<Long> ids) throws Exception {
        productService.delete(ids);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "Delete product type successfully", null));

    }

}
