package com.example.HackathonEvaluator.dto;

import com.example.HackathonEvaluator.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String name;
    private String email;
    private User.Role role;
    private Long teamId;
    private boolean isActive;
}
