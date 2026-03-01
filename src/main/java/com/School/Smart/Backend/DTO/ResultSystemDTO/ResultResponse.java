package com.School.Smart.Backend.DTO.ResultSystemDTO;

import java.util.Map;

import com.School.Smart.Backend.entity.ResultSystemEntity.PassStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResultResponse {
    private Long id;
    
    private Map<String, Integer> subjects;
    private Integer totalMarksObtained;
    private Integer totalMarks;
    private Double percentage;
    private PassStatus passStatus;
}