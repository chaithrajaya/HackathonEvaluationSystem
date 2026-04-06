package com.example.HackathonEvaluator.controller;

import com.example.HackathonEvaluator.dto.SubmissionDto;
import com.example.HackathonEvaluator.dto.EvaluationDto;
import com.example.HackathonEvaluator.service.SubmissionService;
import com.example.HackathonEvaluator.service.EvaluationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping
    @PreAuthorize("hasRole('PARTICIPANT')")
    public ResponseEntity<?> createSubmission(@Valid @RequestBody SubmissionDto submissionDto) {
        try {
            SubmissionDto createdSubmission = submissionService.createSubmission(submissionDto);
            return ResponseEntity.ok(createdSubmission);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('JUDGE')")
    public ResponseEntity<SubmissionDto> getSubmissionById(@PathVariable Long id) {
        SubmissionDto submission = submissionService.getSubmissionById(id);
        return ResponseEntity.ok(submission);
    }

    @GetMapping("/team/{teamId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('JUDGE') or @submissionService.getSubmissionById(#teamId).teamId == authentication.principal.teamId")
    public ResponseEntity<List<SubmissionDto>> getSubmissionsByTeam(@PathVariable Long teamId) {
        List<SubmissionDto> submissions = submissionService.getSubmissionsByTeam(teamId);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/hackathon/{hackathonId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('JUDGE')")
    public ResponseEntity<List<SubmissionDto>> getSubmissionsByHackathon(@PathVariable Long hackathonId) {
        List<SubmissionDto> submissions = submissionService.getSubmissionsByHackathon(hackathonId);
        return ResponseEntity.ok(submissions);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PARTICIPANT') and @submissionService.getSubmissionById(#id).teamId == authentication.principal.teamId")
    public ResponseEntity<?> updateSubmission(@PathVariable Long id, @Valid @RequestBody SubmissionDto submissionDto) {
        try {
            SubmissionDto updatedSubmission = submissionService.updateSubmission(id, submissionDto);
            return ResponseEntity.ok(updatedSubmission);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PARTICIPANT') and @submissionService.getSubmissionById(#id).teamId == authentication.principal.teamId")
    public ResponseEntity<?> deleteSubmission(@PathVariable Long id) {
        try {
            submissionService.deleteSubmission(id);
            return ResponseEntity.ok("Submission deleted successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}/evaluations")
    @PreAuthorize("hasRole('ADMIN') or hasRole('JUDGE') or @submissionService.getSubmissionById(#id).teamId == authentication.principal.teamId")
    public ResponseEntity<?> getSubmissionEvaluations(@PathVariable Long id) {
        try {
            List<EvaluationDto> evaluations = evaluationService.getEvaluationsBySubmission(id);
            return ResponseEntity.ok(evaluations);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
