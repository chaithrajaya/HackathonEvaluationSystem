package com.example.HackathonEvaluator.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HackathonDto {
    private Long hackathonId;
    
    @NotBlank(message = "Hackathon name is required")
    @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
    private String name;
    
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;
    
    @NotNull(message = "Start date is required")
    @Future(message = "Start date must be in the future")
    private LocalDateTime startDate;
    
    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    private LocalDateTime endDate;
    
    @NotNull(message = "Created by user ID is required")
    private Long createdByUserId;
}
