package com.School.Smart.Backend.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Information
    @NotBlank(message = "Full name is required")
    @Column(name = "full_name", nullable = false)
    private String Fullname;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(unique = true, nullable = false)
    private String email;

    
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Student-Specific Fields (nullable for teachers)
    private String fatherName;
    private String motherName;
    private String guardianName;

    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    @Pattern(regexp = "^[0-9]*$", message = "Phone number must contain only digits")
    private String emergencyNo;

    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    @Pattern(regexp = "^[0-9]*$", message = "Phone number must contain only digits")
    private String guardianNo;

    private String className;
    private String section;
    @ElementCollection
    @CollectionTable(name = "user_subjects", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "subject")
    private List<String> subject;

    
    private boolean isVerified = false; // After OTP
    private boolean isApproved = false; // After Admin/Head approval
    private boolean profileCompleted = false;

    
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    private LocalDateTime createdAt = LocalDateTime.now();
}