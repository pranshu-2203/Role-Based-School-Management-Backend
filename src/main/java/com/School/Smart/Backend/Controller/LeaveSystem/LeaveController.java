package com.School.Smart.Backend.Controller.LeaveSystem;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.School.Smart.Backend.DTO.LeaveSystem.LeaveRequest;
import com.School.Smart.Backend.Service.LeaveSystemService.LeaveService;
import com.School.Smart.Backend.entity.LeaveSystemEntity.Leave;
import com.School.Smart.Backend.entity.LeaveSystemEntity.LeaveStatus;
import com.School.Smart.Backend.security.CustomUserDetails;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/Apply")
    public ResponseEntity<?> applyLeave(
            @RequestBody LeaveRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        leaveService.applyLeave(request, userDetails.getUsername());
        return ResponseEntity.ok("Leave applied successfully");
    }

    @GetMapping("/Leave-Status")
    public List<Leave> viewMyLeaves(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return leaveService.getStudentLeaves(userDetails.getUsername());
    }

    @GetMapping("/Requested-Leaves")
    public Page<Leave> viewPendingLeaves(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ParameterObject Pageable pageable) {

        if (userDetails == null) {
            throw new RuntimeException("User not authenticated");
        }

        return leaveService.getPendingLeaves(userDetails.getUsername(), pageable);
    }

    @PutMapping("/{leaveId}")
    public Leave updateLeave(
            @PathVariable Long leaveId,
            @RequestParam LeaveStatus status,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return leaveService.updateLeaveStatus(
                leaveId,
                status,
                userDetails.getUsername());
    }
}