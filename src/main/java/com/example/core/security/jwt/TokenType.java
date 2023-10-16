package com.example.core.security.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenType {
    String token;
    Long expiresIn;
}
