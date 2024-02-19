package com.example.core.controller;

import com.example.core.entity.CartItem;
import com.example.core.helper.ResponseObj;
import com.example.core.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RequestMapping("/api/cart")
@RestController
@CrossOrigin
public class CartController {
    @Autowired
    CartService cartService;

    @Transactional(rollbackOn = Exception.class)
    @ApiOperation(value = "create new product")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_USER')")
    @PostMapping(path = "")
    @CrossOrigin
    public ResponseEntity create(@RequestBody CartItem cartItem) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "save user successfully", cartService.addToCart(cartItem)));
    }
    @Transactional(rollbackOn = Exception.class)
    @ApiOperation(value = "create new product")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN,ROLE_USER')")
    @GetMapping(path = "")
    @CrossOrigin
    public ResponseEntity get() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "save user successfully", cartService.getCartItem()));
    }
}
