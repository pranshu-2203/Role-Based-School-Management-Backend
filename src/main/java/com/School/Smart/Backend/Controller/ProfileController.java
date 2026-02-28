package com.School.Smart.Backend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.Smart.Backend.DTO.ProfileCompletionRequest;
import com.School.Smart.Backend.Service.RegistrationService;
import com.School.Smart.Backend.security.CustomUserDetails;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final RegistrationService registrationService;

    @PostMapping("/complete")
    public ResponseEntity<?> completeProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody ProfileCompletionRequest request) {

        Long userId = userDetails.getUser().getId();
        registrationService.completeProfile(userId, request);

        return ResponseEntity.ok("Profile completed successfully");
    }

    
}