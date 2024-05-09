package com.example.core.controller;

import com.example.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

@Autowired
UserService userService;
//    @RequestMapping(path = "/profile", method = PUT, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
//    public ResponseEntity updateProfile(@ModelAttribute ProfileRequest profileRequest, HttpServletRequest httpServletRequest) {
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "update profile user successfully", userService.updateProfile(profileRequest, httpServletRequest)));
//    }

//    @PutMapping("/password")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
//    public ResponseEntity updatePassword(@RequestBody PasswordUpdate passwordUpdate, HttpServletRequest httpServletRequest) {
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "update password successfully", userService.updatePassword(passwordUpdate, httpServletRequest)));
//    }
}
