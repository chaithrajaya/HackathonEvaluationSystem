package com.example.HackathonEvaluator.repository;

import com.example.HackathonEvaluator.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    List<Evaluation> findBySubmissionId(Long submissionId);
    
    List<Evaluation> findByJudgeUserId(Long judgeUserId);
    
    @Query("SELECT e FROM Evaluation e WHERE e.evaluationId = :evaluationId")
    Optional<Evaluation> findByEvaluationId(@Param("evaluationId") Long evaluationId);
}
