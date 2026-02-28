package com.School.Smart.Backend.repository.LeaveSystemRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.School.Smart.Backend.entity.LeaveSystemEntity.Leave;
import com.School.Smart.Backend.entity.LeaveSystemEntity.LeaveStatus;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    Optional<Leave> findByStudentNameAndStatus(String studentName, LeaveStatus status);

    List<Leave> findByStudentId(Long studentId);
}
