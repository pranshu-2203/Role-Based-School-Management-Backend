package com.School.Smart.Backend.DTO.ResultSystemDTO;



import java.util.Map;

import com.School.Smart.Backend.entity.ResultSystemEntity.ExamType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultRequest {

    private Long studentId;
    private String studentName;
    private Map<String, Integer> subjects;
    //private Integer marksObtained;
    private Integer totalMarks;   
    private ExamType examType;
}