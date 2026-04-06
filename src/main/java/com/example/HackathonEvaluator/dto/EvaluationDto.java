package com.example.HackathonEvaluator.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EvaluationDto {
    private Long evaluationId;
    
    @NotNull(message = "Submission ID is required")
    private Long submissionId;
    
    @NotNull(message = "Judge user ID is required")
    private Long judgeUserId;
    
    @NotNull(message = "Score is required")
    @Min(value = 0, message = "Score must be at least 0")
    @Max(value = 100, message = "Score must not exceed 100")
    private Integer score;
    
    @Size(max = 1000, message = "Feedback must not exceed 1000 characters")
    private String feedback;
}
