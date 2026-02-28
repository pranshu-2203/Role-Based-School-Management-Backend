package com.School.Smart.Backend.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.School.Smart.Backend.DTO.LoginRequest;
import com.School.Smart.Backend.DTO.LoginResponse;
import com.School.Smart.Backend.model.User;
import com.School.Smart.Backend.repository.UserRepository;
import com.School.Smart.Backend.security.CustomUserDetails;
import com.School.Smart.Backend.security.JwtService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userRepository.findByEmail(userDetails.getUsername()).get();

        String token = jwtService.generateToken(user.getId(), user.getEmail(), user.getRole().name());

        LoginResponse response = new LoginResponse(token, user.getRole().name(), user.getId());
        return ResponseEntity.ok(response);
    }
}