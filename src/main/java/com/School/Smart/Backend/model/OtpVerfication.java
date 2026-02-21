package com.School.Smart.Backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OtpVerfication {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    //Email that has to be registered
    @Column(nullable=false)
    private String email;

    //6-digit otp genrated and sent to email
    @Column(nullable=false)
    private String otpCode;

    //expiry time of otp e.g. 5min
    @Column(nullable=false)
    private LocalDateTime expiryTime;

    //prevent reuse
    private boolean isUsed=false;

    private LocalDateTime createdAt=LocalDateTime.now();
}
