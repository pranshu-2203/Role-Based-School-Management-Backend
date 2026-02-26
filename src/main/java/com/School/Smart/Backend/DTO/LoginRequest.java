package com.School.Smart.Backend.DTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
