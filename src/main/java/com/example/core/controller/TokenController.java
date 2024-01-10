package com.example.core.controller;

import com.example.core.dto.request.TokenRequest;
import com.example.core.helper.ResponseObj;
import com.example.core.service.TokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/token")
@RestController
@CrossOrigin
public class TokenController {
    @Autowired
    private TokenService tokenService;
//     @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER', 'ROLE_AUTHOR')")
    @ApiOperation(value ="refresh token")
    @PostMapping("/refreshToken")
    @CrossOrigin

    public ResponseEntity refreshToken(@RequestBody TokenRequest token)  {
        return ResponseEntity.status(HttpStatus.OK).body(tokenService.refreshToken(token.getRfToken()));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER', 'ROLE_AUTHOR')")
    @ApiOperation(value ="update refresh token")
    @PostMapping("/updateRefreshToken")
    @CrossOrigin
    public ResponseEntity updateRefreshToken(@RequestBody TokenRequest token)  {
        tokenService.updateRefreshToken(token.getRfToken(), token.getNewRFToken());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "update refresh token successfully", null));
    }


}
