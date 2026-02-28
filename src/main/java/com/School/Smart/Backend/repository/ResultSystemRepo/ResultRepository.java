package com.School.Smart.Backend.repository.ResultSystemRepo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.School.Smart.Backend.entity.ResultSystemEntity.Result;

public interface ResultRepository extends JpaRepository<Result, Long> {
    List<Result> findByStudentId(Long studentId);
}
