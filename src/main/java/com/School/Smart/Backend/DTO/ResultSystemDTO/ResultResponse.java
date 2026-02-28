package com.School.Smart.Backend.DTO.ResultSystemDTO;

import java.util.Map;

import com.School.Smart.Backend.entity.ResultSystemEntity.PassStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultResponse {

    private String studentName;
    private Map<String, Integer> subjects;
    private Integer marksObtained;
    private Integer totalMarks;
    private Double percentage;
    private PassStatus passStatus;

    public ResultResponse(String studentName,
            Map<String, Integer> subjects,
            Integer marksObtained,
            Integer totalMarks,
            Double percentage,
            PassStatus passStatus) {

        this.studentName = studentName;
        this.subjects = subjects;
        this.marksObtained = marksObtained;
        this.totalMarks = totalMarks;
        this.percentage = percentage;
        this.passStatus = passStatus;
    }
}