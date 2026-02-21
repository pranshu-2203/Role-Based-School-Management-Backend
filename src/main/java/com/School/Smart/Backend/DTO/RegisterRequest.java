package com.School.Smart.Backend.DTO;

import com.School.Smart.Backend.model.Role;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Full name is required")
    @Column(name = "full_name",nullable=false)
    private String fullname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be 8-20 characters")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Password must contain only letters and numbers")
    private String password;

    private Role role;

}
