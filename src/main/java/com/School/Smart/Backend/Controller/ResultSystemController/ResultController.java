package com.School.Smart.Backend.Controller.ResultSystemController;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.School.Smart.Backend.DTO.ResultSystemDTO.ResultRequest;
import com.School.Smart.Backend.DTO.ResultSystemDTO.ResultResponse;
import com.School.Smart.Backend.Service.ResultSystemService.ResultService;
import com.School.Smart.Backend.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Result")
public class ResultController {

    private final ResultService resultService;
    private final JwtService jwtService;

    public ResultController(ResultService resultService,
            JwtService jwtService) {
        this.resultService = resultService;
        this.jwtService = jwtService;
    }

    @PreAuthorize("hasRole('CLASS_TEACHER')")
    @PostMapping("/Add")
    public ResultResponse addResult(@RequestBody ResultRequest request) {
        return resultService.addResult(request);
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")

    public List<ResultResponse> getMyResult(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);

        Long userId = jwtService.extractUserId(token);

        return resultService.getResultByStudentId(userId);
    }
}
