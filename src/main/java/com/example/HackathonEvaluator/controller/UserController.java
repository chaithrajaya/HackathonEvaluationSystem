package com.example.HackathonEvaluator.controller;

import com.example.HackathonEvaluator.dto.UserDto;
import com.example.HackathonEvaluator.entity.User;
import com.example.HackathonEvaluator.service.UserService;
import com.example.HackathonEvaluator.service.LeaderboardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LeaderboardService leaderboardService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto userDto) {
        try {
            UserDto registeredUser = userService.registerUser(userDto);
            return ResponseEntity.ok(registeredUser);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#id).email == authentication.name")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#id).email == authentication.name")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        try {
            UserDto updatedUser = userService.updateUser(id, userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto loginRequest) {
        // This endpoint is handled by AuthController, but keeping for completeness
        return ResponseEntity.badRequest().body("Use /api/auth/login instead");
    }

    @GetMapping("/{id}/submissions")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#id).email == authentication.name")
    public ResponseEntity<?> getUserSubmissions(@PathVariable Long id) {
        try {
            List<Map<String, Object>> submissions = leaderboardService.getUserSubmissions(id);
            return ResponseEntity.ok(submissions);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
