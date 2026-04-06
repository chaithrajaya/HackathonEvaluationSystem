package com.example.HackathonEvaluator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private Long teamId;
    private String teamName;
    private Long hackathonId;
}
