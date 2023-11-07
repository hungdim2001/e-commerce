package com.example.core.dto;

import com.example.core.entity.AuditTable;
import com.example.core.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class UserDto  extends AuditTable {
    private String fullName;
    private String email;
    private String username;
    private String role;
    private String avatarUrl;
    private Boolean status;

}
