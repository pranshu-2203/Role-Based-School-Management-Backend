package com.School.Smart.Backend.Service.LeaveSystemService;

import java.util.List;

import org.springframework.stereotype.Service;

import com.School.Smart.Backend.DTO.LeaveSystem.LeaveRequest;
import com.School.Smart.Backend.DTO.LeaveSystem.LeaveResponse;
import com.School.Smart.Backend.entity.LeaveSystemEntity.Leave;
import com.School.Smart.Backend.entity.LeaveSystemEntity.LeaveStatus;
import com.School.Smart.Backend.exception.LeaveSystem.LeaveException;
import com.School.Smart.Backend.repository.LeaveSystemRepo.LeaveRepository;

@Service
public class LeaveService {
    private final LeaveRepository leaveRepository;

    public LeaveService(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;

    }

    public LeaveResponse applyLeave(LeaveRequest leaveRequest) {
        Leave leave = new Leave();
        leave.setStudentId(leaveRequest.getStudentid());
        leave.setFromDate(leaveRequest.getFromDate());
        leave.setToDate(leaveRequest.getToDate());
        leave.setReason(leaveRequest.getReason());
        leave.setStatus(LeaveStatus.PENDING);

        leave = leaveRepository.save(leave);
        return new LeaveResponse(leave.getId(), leave.getStatus(), "Leave applied successfully");
    }

    public LeaveResponse approveLeave(String studentName) {
        Leave leave = leaveRepository.findByStudentNameAndStatus(studentName, LeaveStatus.PENDING)
                .orElseThrow(() -> new LeaveException("Pending leave not found for student: " + studentName));
        leave.setStatus(LeaveStatus.APPROVED);
        leaveRepository.save(leave);
        return new LeaveResponse(leave.getId(), leave.getStatus(), "Leave approved for " + studentName);
    }

    public LeaveResponse rejectLeave(String studentName) {
        Leave leave = leaveRepository.findByStudentNameAndStatus(studentName, LeaveStatus.PENDING)
                .orElseThrow(() -> new LeaveException("Pending leave not found for student: " + studentName));
        leave.setStatus(LeaveStatus.REJECTED);
        leaveRepository.save(leave);
        return new LeaveResponse(leave.getId(), leave.getStatus(), "Leave rejected for " + studentName);
    }

    public List<Leave> getLeaveStatus(Long studentId) {
        return leaveRepository.findByStudentId(studentId);
    }
}
