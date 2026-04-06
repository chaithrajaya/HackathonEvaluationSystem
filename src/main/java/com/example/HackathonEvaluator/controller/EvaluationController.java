package com.example.HackathonEvaluator.controller;

import com.example.HackathonEvaluator.dto.EvaluationDto;
import com.example.HackathonEvaluator.service.EvaluationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping
    @PreAuthorize("hasRole('JUDGE')")
    public ResponseEntity<?> createEvaluation(@Valid @RequestBody EvaluationDto evaluationDto) {
        try {
            EvaluationDto createdEvaluation = evaluationService.createEvaluation(evaluationDto);
            return ResponseEntity.ok(createdEvaluation);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('JUDGE')")
    public ResponseEntity<EvaluationDto> getEvaluationById(@PathVariable Long id) {
        EvaluationDto evaluation = evaluationService.getEvaluationById(id);
        return ResponseEntity.ok(evaluation);
    }

    @GetMapping("/submission/{submissionId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('JUDGE')")
    public ResponseEntity<List<EvaluationDto>> getEvaluationsBySubmission(@PathVariable Long submissionId) {
        List<EvaluationDto> evaluations = evaluationService.getEvaluationsBySubmission(submissionId);
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/judge/{judgeUserId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('JUDGE') and #judgeUserId == authentication.principal.userId)")
    public ResponseEntity<List<EvaluationDto>> getEvaluationsByJudge(@PathVariable Long judgeUserId) {
        List<EvaluationDto> evaluations = evaluationService.getEvaluationsByJudge(judgeUserId);
        return ResponseEntity.ok(evaluations);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('JUDGE') and @evaluationService.getEvaluationById(#id).judgeUserId == authentication.principal.userId")
    public ResponseEntity<?> updateEvaluation(@PathVariable Long id, @Valid @RequestBody EvaluationDto evaluationDto) {
        try {
            EvaluationDto updatedEvaluation = evaluationService.updateEvaluation(id, evaluationDto);
            return ResponseEntity.ok(updatedEvaluation);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEvaluation(@PathVariable Long id) {
        try {
            evaluationService.deleteEvaluation(id);
            return ResponseEntity.ok("Evaluation deleted successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
