package com.example.checking.repository;

import com.example.checking.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {
}