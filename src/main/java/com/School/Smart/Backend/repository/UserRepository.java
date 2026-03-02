package com.School.Smart.Backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.School.Smart.Backend.model.Role;
import com.School.Smart.Backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByIsApprovedFalse();
    Optional<User> findByClassNameAndSectionAndRole( //for Leave System
            String className,
            String section,
            Role role
    );

}