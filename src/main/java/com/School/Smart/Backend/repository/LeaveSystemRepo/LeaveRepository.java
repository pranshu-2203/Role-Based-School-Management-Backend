package com.School.Smart.Backend.repository.LeaveSystemRepo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.School.Smart.Backend.entity.LeaveSystemEntity.Leave;
import com.School.Smart.Backend.entity.LeaveSystemEntity.LeaveStatus;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
   
    List<Leave> findByStudentId(Long studentId);
    List<Leave> findByTeacherIdAndStatus(Long teacherId, LeaveStatus status);
}
