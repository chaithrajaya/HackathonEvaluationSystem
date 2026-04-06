package com.example.HackathonEvaluator.controller;

import com.example.HackathonEvaluator.dto.TeamDto;
import com.example.HackathonEvaluator.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createTeam(@Valid @RequestBody TeamDto teamDto) {
        try {
            TeamDto createdTeam = teamService.createTeam(teamDto);
            return ResponseEntity.ok(createdTeam);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('JUDGE')")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable Long id) {
        TeamDto team = teamService.getTeamById(id);
        return ResponseEntity.ok(team);
    }

    @GetMapping("/hackathon/{hackathonId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('JUDGE')")
    public ResponseEntity<List<TeamDto>> getTeamsByHackathon(@PathVariable Long hackathonId) {
        List<TeamDto> teams = teamService.getTeamsByHackathon(hackathonId);
        return ResponseEntity.ok(teams);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTeam(@PathVariable Long id, @Valid @RequestBody TeamDto teamDto) {
        try {
            TeamDto updatedTeam = teamService.updateTeam(id, teamDto);
            return ResponseEntity.ok(updatedTeam);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        try {
            teamService.deleteTeam(id);
            return ResponseEntity.ok("Team deleted successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
