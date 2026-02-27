package com.School.Smart.Backend.DTO;


import java.util.List;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileCompletionRequest {

   

    private String fatherName;
    private String motherName;
    private String guardianName;

    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    @Pattern(regexp = "^[0-9]*$", message = "Phone number must contain only digits")
    private String guardianNo;

    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    @Pattern(regexp = "^[0-9]*$", message = "Phone number must contain only digits")
    private String emergencyNo;

    // ----- Teacher Fields -----

    private List<String> subjects;
}