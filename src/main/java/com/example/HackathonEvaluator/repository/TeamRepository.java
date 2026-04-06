package com.example.HackathonEvaluator.repository;

import com.example.HackathonEvaluator.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findByHackathonId(Long hackathonId);
    
    @Query("SELECT t FROM Team t WHERE t.teamId = :teamId")
    Optional<Team> findByTeamId(@Param("teamId") Long teamId);
}
