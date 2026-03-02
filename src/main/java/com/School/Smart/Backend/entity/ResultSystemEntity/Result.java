package com.School.Smart.Backend.entity.ResultSystemEntity;

import java.time.LocalDate;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"studentId", "examType"}))
@Getter
@Setter
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private String studentName;

    @ElementCollection
    @CollectionTable(name = "subject_marks", joinColumns = @JoinColumn(name = "result_id"))
    @MapKeyColumn(name = "subject")
    @Column(name = "marks")
    private Map<String, Integer> subjects;

    private Integer totalMarksObtained;

    private Integer totalMarks;

    private Double percentage;

    @Enumerated(EnumType.STRING)
    private ExamType examType;

    private LocalDate publishDate;

    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    private PassStatus passStatus;
}
