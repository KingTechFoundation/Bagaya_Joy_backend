package com.example.checking.controller;

import com.example.checking.model.Candidate;
import com.example.checking.Services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vote")
public class VoteController {

    @Autowired
    private PollService pollService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{candidateId}/{userId}")
    public String vote(@PathVariable Long candidateId, @PathVariable Long userId) {
        return pollService.castVote(candidateId, userId);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/results/{pollId}")
    public List<Candidate> viewResults(@PathVariable Long pollId) {
        return pollService.getResults(pollId);
    }
}

