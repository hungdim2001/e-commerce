package com.example.core.controller;

import com.example.core.dto.request.CreateRoleRequest;
import com.example.core.entity.Rating;
import com.example.core.helper.ResponseObj;
import com.example.core.service.RatingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/rating")
@RestController
@CrossOrigin
public class RatingController {
    @Autowired
    RatingService ratingService;
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @ApiOperation(value = "create comment")
    @PostMapping("")
    @CrossOrigin
    public ResponseEntity createRole(@RequestBody Rating rating) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "create comment", ratingService.createComment(rating)));
    }
}
