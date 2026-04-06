package com.example.HackathonEvaluator.service;

import com.example.HackathonEvaluator.dto.SubmissionDto;
import com.example.HackathonEvaluator.entity.Submission;
import com.example.HackathonEvaluator.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    public SubmissionDto createSubmission(SubmissionDto submissionDto) {
        Submission submission = new Submission();
        submission.setTeamId(submissionDto.getTeamId());
        submission.setHackathonId(submissionDto.getHackathonId());
        submission.setProjectTitle(submissionDto.getProjectTitle());
        submission.setGithubLink(submissionDto.getGithubLink());
        submission.setDescription(submissionDto.getDescription());

        Submission savedSubmission = submissionRepository.save(submission);
        return mapToSubmissionDto(savedSubmission);
    }

    public SubmissionDto getSubmissionById(Long id) {
        Submission submission = submissionRepository.findBySubmissionId(id)
                .orElseThrow(() -> new RuntimeException("Submission not found with id: " + id));
        return mapToSubmissionDto(submission);
    }

    public List<SubmissionDto> getSubmissionsByTeam(Long teamId) {
        return submissionRepository.findByTeamId(teamId).stream()
                .map(this::mapToSubmissionDto)
                .collect(Collectors.toList());
    }

    public List<SubmissionDto> getSubmissionsByHackathon(Long hackathonId) {
        return submissionRepository.findByHackathonId(hackathonId).stream()
                .map(this::mapToSubmissionDto)
                .collect(Collectors.toList());
    }

    public SubmissionDto updateSubmission(Long id, SubmissionDto submissionDto) {
        Submission submission = submissionRepository.findBySubmissionId(id)
                .orElseThrow(() -> new RuntimeException("Submission not found with id: " + id));

        submission.setTeamId(submissionDto.getTeamId());
        submission.setHackathonId(submissionDto.getHackathonId());
        submission.setProjectTitle(submissionDto.getProjectTitle());
        submission.setGithubLink(submissionDto.getGithubLink());
        submission.setDescription(submissionDto.getDescription());

        Submission updatedSubmission = submissionRepository.save(submission);
        return mapToSubmissionDto(updatedSubmission);
    }

    public void deleteSubmission(Long id) {
        Submission submission = submissionRepository.findBySubmissionId(id)
                .orElseThrow(() -> new RuntimeException("Submission not found with id: " + id));
        submissionRepository.delete(submission);
    }

    private SubmissionDto mapToSubmissionDto(Submission submission) {
        SubmissionDto submissionDto = new SubmissionDto();
        submissionDto.setSubmissionId(submission.getSubmissionId());
        submissionDto.setTeamId(submission.getTeamId());
        submissionDto.setHackathonId(submission.getHackathonId());
        submissionDto.setProjectTitle(submission.getProjectTitle());
        submissionDto.setGithubLink(submission.getGithubLink());
        submissionDto.setDescription(submission.getDescription());
        return submissionDto;
    }
}
