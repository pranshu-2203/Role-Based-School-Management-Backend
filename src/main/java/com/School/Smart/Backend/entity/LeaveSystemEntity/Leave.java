package com.School.Smart.Backend.entity.LeaveSystemEntity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Student_leave")
@Getter
@Setter
public class Leave {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    private String studentName;
    private Long studentId;

    private String className;
    private String section;
    private String gurdianNo;
    
    private Long teacherId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    @Enumerated(EnumType.STRING)
    public LeaveStatus status;
}

