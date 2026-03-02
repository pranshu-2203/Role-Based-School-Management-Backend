package com.School.Smart.Backend.repository.ResultSystemRepo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.School.Smart.Backend.entity.ResultSystemEntity.ExamType;
import com.School.Smart.Backend.entity.ResultSystemEntity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByStudentIdAndExpiryDateAfter(Long studentId, LocalDate date);

    List<Result> findByStudentIdAndExamTypeAndExpiryDateAfter(
            Long studentId,
            ExamType examType,
            LocalDate date);
    boolean existsByStudentIdAndExamTypeAndExpiryDateAfter(
        Long studentId,
        ExamType examType,
        LocalDate date
);
}
