package com.example.checking.repository;

import com.example.checking.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findByPollId(Long pollId);
}
