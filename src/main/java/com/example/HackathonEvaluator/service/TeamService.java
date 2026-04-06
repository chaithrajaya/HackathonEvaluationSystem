package com.example.HackathonEvaluator.service;

import com.example.HackathonEvaluator.dto.TeamDto;
import com.example.HackathonEvaluator.entity.Team;
import com.example.HackathonEvaluator.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public TeamDto createTeam(TeamDto teamDto) {
        Team team = new Team();
        team.setTeamName(teamDto.getTeamName());
        team.setHackathonId(teamDto.getHackathonId());

        Team savedTeam = teamRepository.save(team);
        return mapToTeamDto(savedTeam);
    }

    public TeamDto getTeamById(Long id) {
        Team team = teamRepository.findByTeamId(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
        return mapToTeamDto(team);
    }

    public List<TeamDto> getTeamsByHackathon(Long hackathonId) {
        return teamRepository.findByHackathonId(hackathonId).stream()
                .map(this::mapToTeamDto)
                .collect(Collectors.toList());
    }

    public TeamDto updateTeam(Long id, TeamDto teamDto) {
        Team team = teamRepository.findByTeamId(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));

        team.setTeamName(teamDto.getTeamName());
        team.setHackathonId(teamDto.getHackathonId());

        Team updatedTeam = teamRepository.save(team);
        return mapToTeamDto(updatedTeam);
    }

    public void deleteTeam(Long id) {
        Team team = teamRepository.findByTeamId(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
        teamRepository.delete(team);
    }

    private TeamDto mapToTeamDto(Team team) {
        TeamDto teamDto = new TeamDto();
        teamDto.setTeamId(team.getTeamId());
        teamDto.setTeamName(team.getTeamName());
        teamDto.setHackathonId(team.getHackathonId());
        return teamDto;
    }
}
