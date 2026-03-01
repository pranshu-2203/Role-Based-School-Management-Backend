package com.School.Smart.Backend.Service.ResultSystemService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.School.Smart.Backend.DTO.ResultSystemDTO.ResultRequest;
import com.School.Smart.Backend.DTO.ResultSystemDTO.ResultResponse;
import com.School.Smart.Backend.entity.ResultSystemEntity.PassStatus;
import com.School.Smart.Backend.entity.ResultSystemEntity.Result;
import com.School.Smart.Backend.repository.ResultSystemRepo.ResultRepository;

@Service
public class ResultService {

    private final ResultRepository resultRepository;

    public ResultService(ResultRepository resultRepository) {
        this.resultRepository = resultRepository;
    }

    public ResultResponse addResult(ResultRequest resultRequest) {

        

        if (resultRequest.getTotalMarks() == null || resultRequest.getTotalMarks() == 0) {
            throw new IllegalArgumentException("Total marks must be greater than 0");
        }

        if (resultRequest.getSubjects() == null || resultRequest.getSubjects().isEmpty()) {
            throw new IllegalArgumentException("Subjects cannot be empty");
        }

        // 🔥 Calculate total obtained from subjects map
        int totalObtained = resultRequest.getSubjects()
                .values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();

        Result result = new Result();
        result.setStudentId(resultRequest.getStudentId());
        result.setStudentName(resultRequest.getStudentName());
        result.setSubjects(resultRequest.getSubjects());
        result.setTotalMarksObtained(totalObtained); // ✅ fixed
        result.setTotalMarks(resultRequest.getTotalMarks());

        double percentage = (totalObtained * 100.0)
                / resultRequest.getTotalMarks();

        result.setPercentage(percentage);
        result.setPassStatus(getPassStatus(percentage));


         result.setExamType(resultRequest.getExamType());

    LocalDate today = LocalDate.now();
    result.setPublishDate(today);

    LocalDate expiryDate;

    switch (resultRequest.getExamType()) {
        case MONTHLY:
            expiryDate = today.plusDays(15);
            break;
        case FIRST_TERM:
        case SECOND_TERM:
            expiryDate = today.plusDays(20);
            break;
        case QUARTERLY:
            expiryDate = today.plusMonths(1);
            break;
        case HALF_YEARLY:
            expiryDate = today.plusMonths(1);
            break;
        case ANNUAL:
            expiryDate = today.plusMonths(3);
            break;
        default:
            throw new IllegalArgumentException("Invalid exam type");
    }

    result.setExpiryDate(expiryDate);


        result = resultRepository.save(result);

        return new ResultResponse(
                result.getId(),
                result.getSubjects(),
                result.getTotalMarksObtained(),
                result.getTotalMarks(),
                result.getPercentage(),
                result.getPassStatus());
    }

    private PassStatus getPassStatus(double percentage) {

        if (percentage == 100) {
            return PassStatus.OUTSTANDING;

        } else if (percentage >= 90) {
            return PassStatus.EXCELLENT;

        } else if (percentage >= 75) {
            return PassStatus.PASS_WITH_DISTINCTION;

        } else if (percentage >= 50) {
            return PassStatus.PASS_NEED_IMPROVEMENT;

        } else if (percentage >= 33) {
            return PassStatus.MARGINAL_PASS;

        } else {
            return PassStatus.FAIL;
        }
    }

    public List<ResultResponse> getResultByStudentId(Long studentId) {

        

        return resultRepository.findByStudentIdAndExpiryDateAfter(studentId, LocalDate.now())
                .stream()
                .map(r -> new ResultResponse(
                        r.getId(),
                        r.getSubjects(),
                        r.getTotalMarksObtained(),
                        r.getTotalMarks(),
                        r.getPercentage(),
                        r.getPassStatus()))
                .toList();
    }
}