package com.example.core.controller;

import com.example.core.dto.request.AreaRequest;
import com.example.core.helper.ResponseObj;
import com.example.core.service.AreaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/area")
@RestController
@CrossOrigin
public class AreaController {
    @Autowired
    AreaService areaService;

    @GetMapping(value = {"", "/{arg}"})
    @ApiOperation(value = "Get Area")
    @CrossOrigin
    public ResponseEntity getArea(@PathVariable(required = false) String arg) {

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "get area successfully", areaService.getArea(arg)));
    }


}
