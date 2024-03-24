package com.example.core.controller;

import com.example.core.dto.request.OrderRequest;
import com.example.core.helper.ResponseObj;
import com.example.core.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RequestMapping("/api/order")
@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    OrderService orderService;

    @Transactional(rollbackOn = Exception.class)
    @ApiOperation(value = "create new order")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USERj')")
    @PostMapping(path = "/vnpay")
    @CrossOrigin
    public ResponseEntity createVnPay(@RequestBody OrderRequest orderRequest, HttpServletRequest httpServletRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "create order successfully ", orderService.createOrderVnPay(orderRequest, httpServletRequest)));
    }
    @Transactional(rollbackOn = Exception.class)
    @ApiOperation(value = "check order")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(path = "/check")
    @CrossOrigin
    public ResponseEntity createSuccess(HttpServletRequest httpServletRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "check order successfully ", orderService.checkOrder(httpServletRequest)));
    }

}
