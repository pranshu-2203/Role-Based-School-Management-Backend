package com.School.Smart.Backend.DTO;


import com.School.Smart.Backend.model.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenerateInviteRequest {
    @NotBlank
    private Role roleAllowed;
    @NotBlank
    private String className;
    @NotBlank
    private String section;
    private String subject;
    private Long generatedByid;
}
