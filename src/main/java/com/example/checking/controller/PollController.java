package com.example.checking.controller;

import com.example.checking.model.Candidate;
import com.example.checking.model.Poll;
import com.example.checking.Services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/poll")
public class PollController {

    @Autowired
    private PollService pollService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Poll createPoll(@RequestBody Poll poll) {
        for (Candidate candidate : poll.getCandidates()) {
            candidate.setPoll(poll);
        }
        return pollService.createPoll(poll);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Poll updatePoll(@PathVariable Long id, @RequestBody Poll poll) {
        return pollService.updatePoll(id, poll);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePoll(@PathVariable Long id) {
        pollService.deletePoll(id);
    }

    @GetMapping("/all")
    public List<Poll> getPolls() {
        return pollService.getAllPolls();
    }
}
