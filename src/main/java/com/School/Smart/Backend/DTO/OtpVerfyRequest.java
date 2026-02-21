package com.School.Smart.Backend.DTO;

import lombok.Data;

@Data
public class OtpVerfyRequest {
    private String email;
    private String otp;
}
