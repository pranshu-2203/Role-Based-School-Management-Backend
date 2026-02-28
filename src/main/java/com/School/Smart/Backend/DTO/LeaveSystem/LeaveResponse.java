package com.School.Smart.Backend.DTO.LeaveSystem;

import com.School.Smart.Backend.entity.LeaveSystemEntity.LeaveStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveResponse {
    private Long leaveId;
    private LeaveStatus status;
    private String message;

    public LeaveResponse(Long leaveId, LeaveStatus status, String message){
        this.leaveId=leaveId;
        this.status=status;
        this.message=message;
    }
}
