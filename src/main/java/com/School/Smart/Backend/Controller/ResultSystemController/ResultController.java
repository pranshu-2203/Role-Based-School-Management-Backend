package com.School.Smart.Backend.Controller.ResultSystemController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.School.Smart.Backend.DTO.ResultSystemDTO.ResultRequest;
import com.School.Smart.Backend.DTO.ResultSystemDTO.ResultResponse;
import com.School.Smart.Backend.Service.ResultSystemService.ResultService;

@RestController
@RequestMapping("/Result")
public class ResultController {
    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }
    @PostMapping("/Add")
    public ResultResponse addResult(@RequestBody ResultRequest request) {
        return resultService.addResult(request);
    }

    @GetMapping("/Student/{studentId}")
    public List<ResultResponse> getResultsByStudent(@PathVariable Long studentId) {
        return resultService.getResultsByStudent(studentId);
    }
}
