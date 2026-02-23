package com.School.Smart.Backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.School.Smart.Backend.entity.InviteCode;


public interface InviteCodeRepository extends JpaRepository<InviteCode, Long> {
    Optional<InviteCode> findByCode(String code);
    List<InviteCode> findByGeneratedById(Long generatedById);
    
}
