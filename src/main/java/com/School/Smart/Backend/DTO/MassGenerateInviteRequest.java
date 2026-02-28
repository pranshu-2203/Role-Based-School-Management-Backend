package com.School.Smart.Backend.DTO;

import java.util.List;

import com.School.Smart.Backend.model.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MassGenerateInviteRequest {
    @NotNull(message = "Allot role the can register using invite code")
    private Role roleAllowed;
    @NotBlank(message = "Enter Class")
    private String className;
    @NotBlank(message = "Enter class Section")
    private String section;

   
    private List<String> SubjectPerTeacher;
    @NotNull
    private Long generatedById;
    @NotEmpty(message="Names List cannot be Empty")
    private List<String> names;
}
