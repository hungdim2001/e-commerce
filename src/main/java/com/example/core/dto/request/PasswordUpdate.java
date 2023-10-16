package com.example.core.dto.request;

import lombok.Data;

@Data
public class PasswordUpdate {
    private String oldPassword;
    private String newPassword;
}
