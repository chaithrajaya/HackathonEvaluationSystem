package com.example.HackathonEvaluator.repository;

import com.example.HackathonEvaluator.entity.Hackathon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HackathonRepository extends JpaRepository<Hackathon, Long> {

    @Query("SELECT h FROM Hackathon h WHERE h.hackathonId = :hackathonId")
    Optional<Hackathon> findByHackathonId(@Param("hackathonId") Long hackathonId);
    
    List<Hackathon> findByCreatedByUserId(Long createdByUserId);
}
