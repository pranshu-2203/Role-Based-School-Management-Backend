package com.School.Smart.Backend.DTO.ResultSystemDTO;



import java.util.Map;

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
}