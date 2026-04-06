package com.example.HackathonEvaluator.controller;

import com.example.HackathonEvaluator.dto.HackathonDto;
import com.example.HackathonEvaluator.service.HackathonService;
import com.example.HackathonEvaluator.service.LeaderboardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hackathons")
public class HackathonController {

    @Autowired
    private HackathonService hackathonService;

    @Autowired
    private LeaderboardService leaderboardService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createHackathon(@Valid @RequestBody HackathonDto hackathonDto) {
        try {
            HackathonDto createdHackathon = hackathonService.createHackathon(hackathonDto);
            return ResponseEntity.ok(createdHackathon);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<HackathonDto> getHackathonById(@PathVariable Long id) {
        HackathonDto hackathon = hackathonService.getHackathonById(id);
        return ResponseEntity.ok(hackathon);
    }

    @GetMapping
    public ResponseEntity<List<HackathonDto>> getAllHackathons() {
        List<HackathonDto> hackathons = hackathonService.getAllHackathons();
        return ResponseEntity.ok(hackathons);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateHackathon(@PathVariable Long id, @Valid @RequestBody HackathonDto hackathonDto) {
        try {
            HackathonDto updatedHackathon = hackathonService.updateHackathon(id, hackathonDto);
            return ResponseEntity.ok(updatedHackathon);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteHackathon(@PathVariable Long id) {
        try {
            hackathonService.deleteHackathon(id);
            return ResponseEntity.ok("Hackathon deleted successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}/leaderboard")
    public ResponseEntity<?> getLeaderboard(@PathVariable Long id) {
        try {
            List<Map<String, Object>> leaderboard = leaderboardService.getHackathonLeaderboard(id);
            return ResponseEntity.ok(leaderboard);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
