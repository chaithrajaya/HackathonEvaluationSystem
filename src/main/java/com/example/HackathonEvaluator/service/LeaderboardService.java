package com.example.HackathonEvaluator.service;

import com.example.HackathonEvaluator.dto.TeamDto;
import com.example.HackathonEvaluator.entity.Evaluation;
import com.example.HackathonEvaluator.entity.Submission;
import com.example.HackathonEvaluator.entity.Team;
import com.example.HackathonEvaluator.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    public List<Map<String, Object>> getHackathonLeaderboard(Long hackathonId) {
        List<Team> teams = teamRepository.findByHackathonId(hackathonId);
        
        List<Map<String, Object>> leaderboard = new ArrayList<>();
        
        for (Team team : teams) {
            List<Submission> submissions = submissionRepository.findByTeamId(team.getTeamId());
            
            double totalScore = 0;
            int evaluationCount = 0;
            
            for (Submission submission : submissions) {
                List<Evaluation> evaluations = evaluationRepository.findBySubmissionId(submission.getSubmissionId());
                
                for (Evaluation evaluation : evaluations) {
                    totalScore += evaluation.getScore();
                    evaluationCount++;
                }
            }
            
            double averageScore = evaluationCount > 0 ? totalScore / evaluationCount : 0;
            
            Map<String, Object> teamEntry = new HashMap<>();
            teamEntry.put("teamId", team.getTeamId());
            teamEntry.put("teamName", team.getTeamName());
            teamEntry.put("averageScore", averageScore);
            teamEntry.put("totalEvaluations", evaluationCount);
            teamEntry.put("submissionsCount", submissions.size());
            
            leaderboard.add(teamEntry);
        }
        
        // Sort by average score descending
        leaderboard.sort((a, b) -> Double.compare((Double) b.get("averageScore"), (Double) a.get("averageScore")));
        
        // Add rank
        for (int i = 0; i < leaderboard.size(); i++) {
            leaderboard.get(i).put("rank", i + 1);
        }
        
        return leaderboard;
    }

    public List<Map<String, Object>> getUserSubmissions(Long userId) {
        // This would require getting the user's team and then their submissions
        // For now, returning a placeholder implementation
        List<Map<String, Object>> userSubmissions = new ArrayList<>();
        
        // TODO: Implement logic to get user's team and submissions
        Map<String, Object> placeholder = new HashMap<>();
        placeholder.put("message", "User submissions feature to be implemented");
        userSubmissions.add(placeholder);
        
        return userSubmissions;
    }
}
