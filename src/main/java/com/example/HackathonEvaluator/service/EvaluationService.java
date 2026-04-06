package com.example.HackathonEvaluator.service;

import com.example.HackathonEvaluator.dto.EvaluationDto;
import com.example.HackathonEvaluator.entity.Evaluation;
import com.example.HackathonEvaluator.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    public EvaluationDto createEvaluation(EvaluationDto evaluationDto) {
        Evaluation evaluation = new Evaluation();
        evaluation.setSubmissionId(evaluationDto.getSubmissionId());
        evaluation.setJudgeUserId(evaluationDto.getJudgeUserId());
        evaluation.setScore(evaluationDto.getScore());
        evaluation.setFeedback(evaluationDto.getFeedback());

        Evaluation savedEvaluation = evaluationRepository.save(evaluation);
        return mapToEvaluationDto(savedEvaluation);
    }

    public EvaluationDto getEvaluationById(Long id) {
        Evaluation evaluation = evaluationRepository.findByEvaluationId(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found with id: " + id));
        return mapToEvaluationDto(evaluation);
    }

    public List<EvaluationDto> getEvaluationsBySubmission(Long submissionId) {
        return evaluationRepository.findBySubmissionId(submissionId).stream()
                .map(this::mapToEvaluationDto)
                .collect(Collectors.toList());
    }

    public List<EvaluationDto> getEvaluationsByJudge(Long judgeUserId) {
        return evaluationRepository.findByJudgeUserId(judgeUserId).stream()
                .map(this::mapToEvaluationDto)
                .collect(Collectors.toList());
    }

    public EvaluationDto updateEvaluation(Long id, EvaluationDto evaluationDto) {
        Evaluation evaluation = evaluationRepository.findByEvaluationId(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found with id: " + id));

        evaluation.setSubmissionId(evaluationDto.getSubmissionId());
        evaluation.setJudgeUserId(evaluationDto.getJudgeUserId());
        evaluation.setScore(evaluationDto.getScore());
        evaluation.setFeedback(evaluationDto.getFeedback());

        Evaluation updatedEvaluation = evaluationRepository.save(evaluation);
        return mapToEvaluationDto(updatedEvaluation);
    }

    public void deleteEvaluation(Long id) {
        Evaluation evaluation = evaluationRepository.findByEvaluationId(id)
                .orElseThrow(() -> new RuntimeException("Evaluation not found with id: " + id));
        evaluationRepository.delete(evaluation);
    }

    private EvaluationDto mapToEvaluationDto(Evaluation evaluation) {
        EvaluationDto evaluationDto = new EvaluationDto();
        evaluationDto.setEvaluationId(evaluation.getEvaluationId());
        evaluationDto.setSubmissionId(evaluation.getSubmissionId());
        evaluationDto.setJudgeUserId(evaluation.getJudgeUserId());
        evaluationDto.setScore(evaluation.getScore());
        evaluationDto.setFeedback(evaluation.getFeedback());
        return evaluationDto;
    }
}
