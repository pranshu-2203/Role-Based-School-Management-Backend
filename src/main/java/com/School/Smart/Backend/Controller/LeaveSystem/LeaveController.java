package com.School.Smart.Backend.Controller.LeaveSystem;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.Smart.Backend.DTO.LeaveSystem.LeaveRequest;
import com.School.Smart.Backend.DTO.LeaveSystem.LeaveResponse;
import com.School.Smart.Backend.Service.LeaveSystemService.LeaveService;
import com.School.Smart.Backend.entity.LeaveSystemEntity.Leave;

@RestController
@RequestMapping("/Request_Leave")
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/Apply")
    public LeaveResponse applyLeave(
            @RequestBody LeaveRequest leaveRequest) {
        return leaveService.applyLeave(leaveRequest);
    }

    @PutMapping("/Approve_Leave/{studentName}")
    public LeaveResponse approveLeaveByName(@PathVariable String studentName) {
        return leaveService.approveLeave(studentName);
    }

    @PutMapping("/Reject_Leave/{studentName}")
    public LeaveResponse rejectLeaveByName(@PathVariable String studentName) {
        return leaveService.rejectLeave(studentName);
    }

    @GetMapping("/Status/{studentId}")
    public List<Leave> getLeaveStatus(@PathVariable Long studentId) {
        return leaveService.getLeaveStatus(studentId);
    }

}
