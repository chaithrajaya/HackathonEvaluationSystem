package com.example.HackathonEvaluator.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SubmissionDto {
    private Long submissionId;
    
    @NotNull(message = "Team ID is required")
    private Long teamId;
    
    @NotNull(message = "Hackathon ID is required")
    private Long hackathonId;
    
    @NotBlank(message = "Project title is required")
    @Size(min = 3, max = 200, message = "Project title must be between 3 and 200 characters")
    private String projectTitle;
    
    @Pattern(regexp = "^(https?://)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([/\\w \\.-]*)*\\/?$", message = "Invalid URL format")
    private String githubLink;
    
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;
}
