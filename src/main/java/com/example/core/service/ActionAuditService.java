package com.example.core.service;

import com.example.core.entity.ActionAudit;
import com.example.core.repository.ActionAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ActionAuditService {

    @Autowired
    ActionAuditRepository actionAuditRepository;
    public ActionAudit create(ActionAudit actionAudit) {
       return actionAuditRepository.save(actionAudit);
    }
}
