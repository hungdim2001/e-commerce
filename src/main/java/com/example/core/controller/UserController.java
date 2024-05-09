package com.example.core.controller;

import com.example.core.entity.User;
import com.example.core.helper.ResponseObj;
import com.example.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

@Autowired
UserService userService;
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PutMapping(value = {""})
    public ResponseEntity updateProfile(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "update profile user successfully", userService.save(user)));
    }

//    @PutMapping("/password")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
//    public ResponseEntity updatePassword(@RequestBody PasswordUpdate passwordUpdate, HttpServletRequest httpServletRequest) {
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "update password successfully", userService.updatePassword(passwordUpdate, httpServletRequest)));
//    }
}
