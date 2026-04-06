package com.example.HackathonEvaluator.repository;

import com.example.HackathonEvaluator.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByTeamId(Long teamId);
    
    List<Submission> findByHackathonId(Long hackathonId);
    
    @Query("SELECT s FROM Submission s WHERE s.submissionId = :submissionId")
    Optional<Submission> findBySubmissionId(@Param("submissionId") Long submissionId);
}
