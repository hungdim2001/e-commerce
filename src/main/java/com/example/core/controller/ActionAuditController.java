package com.example.core.controller;

import com.example.core.entity.ActionAudit;
import com.example.core.entity.Address;
import com.example.core.helper.ResponseObj;
import com.example.core.service.ActionAuditService;
import com.example.core.util.UserUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/api/log")
@RestController
@CrossOrigin
public class ActionAuditController {
    @Autowired
    ActionAuditService actionAuditService;

    @PostMapping(value = {""})
    @ApiOperation(value = "create log")
    @CrossOrigin
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity create(HttpServletRequest request, @RequestBody ActionAudit actionAudit) {
        actionAudit.setUserAgent(request.getHeader("user-agent"));
        actionAudit.setCreateDatetime(new Date());
        actionAudit.setCreateUser(UserUtil.getUserId());
        actionAudit.setStatus(true);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObj(HttpStatus.OK.value(), true, "create log successfully", actionAuditService.create(actionAudit)));
    }
}
