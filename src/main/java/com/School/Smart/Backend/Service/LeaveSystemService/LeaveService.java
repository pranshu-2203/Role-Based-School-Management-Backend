package com.School.Smart.Backend.Service.LeaveSystemService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.School.Smart.Backend.DTO.LeaveSystem.LeaveRequest;
import com.School.Smart.Backend.entity.LeaveSystemEntity.Leave;
import com.School.Smart.Backend.entity.LeaveSystemEntity.LeaveStatus;
import com.School.Smart.Backend.model.Role;
import com.School.Smart.Backend.model.User;
import com.School.Smart.Backend.repository.UserRepository;
import com.School.Smart.Backend.repository.LeaveSystemRepo.LeaveRepository;

@Service
public class LeaveService {

    private final UserRepository userRepository;
    private final LeaveRepository leaveRepository;

    public LeaveService(UserRepository userRepository,
            LeaveRepository leaveRepository) {
        this.userRepository = userRepository;
        this.leaveRepository = leaveRepository;
    }

   
    public void applyLeave(LeaveRequest request, String email) {

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (student.getRole() != Role.STUDENT) {
            throw new RuntimeException("Only students can apply for leave");
        }

        // Find class teacher of same class & section
        User classTeacher = userRepository
                .findByClassNameAndSectionAndRole(
                        student.getClassName(),
                        student.getSection(),
                        Role.CLASS_TEACHER)
                .orElseThrow(() -> new RuntimeException("Class teacher not found"));

        Leave leave = new Leave();
        leave.setStudentId(student.getId());
        leave.setTeacherId(classTeacher.getId());
        leave.setStudentName(student.getFullname());
        leave.setClassName(student.getClassName());
        leave.setSection(student.getSection());
        leave.setGurdianNo(student.getGuardianNo());
        leave.setFromDate(request.getFromDate());
        leave.setToDate(request.getToDate());
        leave.setReason(request.getReason());
        leave.setStatus(LeaveStatus.PENDING);

        leaveRepository.save(leave);
    }

    public List<Leave> getPendingLeaves(String email) {

        User teacher = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (teacher.getRole() != Role.CLASS_TEACHER) {
            throw new RuntimeException("Only class teachers can view leaves");
        }

        return leaveRepository
                .findByTeacherIdAndStatus(
                        teacher.getId(),
                        LeaveStatus.PENDING);
    }

    
    public Leave updateLeaveStatus(Long leaveId,
            LeaveStatus newStatus,
            String email) {

        User teacher = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (teacher.getRole() != Role.CLASS_TEACHER) {
            throw new RuntimeException("Only class teachers can update leaves");
        }

        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        if (!leave.getTeacherId().equals(teacher.getId())) {
            throw new RuntimeException("Not authorized");
        }

        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new RuntimeException("Leave already processed");
        }

        leave.setStatus(newStatus);
        return leaveRepository.save(leave);
    }

   
    public List<Leave> getStudentLeaves(String email) {

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (student.getRole() != Role.STUDENT) {
            throw new RuntimeException("Only students can view their leaves");
        }

        return leaveRepository.findByStudentId(student.getId());
    }
}