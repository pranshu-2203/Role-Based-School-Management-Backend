package com.School.Smart.Backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.School.Smart.Backend.entity.InviteCode;
import com.School.Smart.Backend.model.Role;

public interface InviteCodeRepository extends JpaRepository<InviteCode, Long> {
    Optional<InviteCode> findByCode(String code);

    Page<InviteCode> findByGeneratedById(Long generatedById, Pageable pageable);

    Page<InviteCode> findByGeneratedByIdAndIsUsed(Long generatedById, Boolean used, Pageable pageable);

    Page<InviteCode> findByGeneratedByIdAndRoleAllowed(Long generatedById, Role roleAllowed, Pageable pageable);

    Page<InviteCode> findByGeneratedByIdAndSubjectContaining(Long generatedById, String subject, Pageable pageable);
}
