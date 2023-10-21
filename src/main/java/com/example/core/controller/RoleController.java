package com.example.core.controller;


import com.example.core.dto.request.CreateRoleRequest;
import com.example.core.dto.request.LogoutRequest;
import com.example.core.helper.ResponseObj;
import com.example.core.service.RoleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "create role")
    @PostMapping("")
    @CrossOrigin
    public ResponseEntity logout(@RequestBody CreateRoleRequest roleRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "create role successfully", roleService.createRole(roleRequest.getRole().toUpperCase())));
    }

}
