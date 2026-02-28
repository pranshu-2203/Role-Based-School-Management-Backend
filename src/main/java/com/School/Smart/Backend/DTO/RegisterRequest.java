package com.School.Smart.Backend.DTO;

import com.School.Smart.Backend.model.Role;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "Full name is required")
    private String Fullname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be 8-20 characters")
    private String password;

    @NotNull(message="Role is Reqiuired")
    private Role role;
    @NotBlank(message = "Invite code is reqiured")
    private String inviteCode;

}
