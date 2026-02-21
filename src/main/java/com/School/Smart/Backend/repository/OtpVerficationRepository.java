package com.School.Smart.Backend.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.School.Smart.Backend.model.OtpVerfication;

public interface OtpVerficationRepository extends JpaRepository<OtpVerfication,Long>{
    Optional<OtpVerfication> findTopByEmailOrderByCreatedAtDesc(String email);

    void deleteByExpiryTimeBefore(LocalDateTime time);


    
}
