package com.School.Smart.Backend.DTO;
import lombok.Data;

@Data
public class LoginResponse {
    private final String token;
    private final String role;
    private final Long userId;
}
