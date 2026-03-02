package com.School.Smart.Backend.DTO.LeaveSystem;


import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveRequest {
    private Long studentid;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private String className;
    private String section;
}
