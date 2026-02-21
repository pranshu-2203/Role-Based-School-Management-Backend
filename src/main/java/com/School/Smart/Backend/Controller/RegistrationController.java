package com.School.Smart.Backend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.Smart.Backend.DTO.OtpVerfyRequest;
import com.School.Smart.Backend.DTO.RegisterRequest;
import com.School.Smart.Backend.Service.RegistrationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    //Register basic user
    @PostMapping("/register")
    public ResponseEntity<String>register(
        @Valid
        @RequestBody 
        RegisterRequest registerRequest) {
        registrationService.registerBasicUser(registerRequest.getFullname(), registerRequest.getEmail(), registerRequest.getPassword(),registerRequest.getRole());
        return ResponseEntity.ok("Registration successful. Otp sent tp email for authentication.");
    }
    
    //Verify otp

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyotp(
        @Valid
        @RequestBody
        OtpVerfyRequest request
    ){
        registrationService.verifyOtp(request.getEmail(), request.getOtp());
        return ResponseEntity.ok("OTP verfied Successfully");
    }

}
