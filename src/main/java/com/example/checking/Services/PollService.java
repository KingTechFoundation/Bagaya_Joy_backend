package com.example.checking.Services;

import com.example.checking.model.Candidate;
import com.example.checking.model.Poll;
import com.example.checking.model.User;
import com.example.checking.repository.CandidateRepository;
import com.example.checking.repository.PollRepository;
import com.example.checking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PollService {
    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private UserRepository userRepository;

    public Poll createPoll(Poll poll) {
        return pollRepository.save(poll);
    }

  public Poll updatePoll(Long id, Poll updatedPoll) {
    Poll poll = pollRepository.findById(id).orElseThrow(() -> new RuntimeException("Poll not found"));

    
    poll.setQuestion(updatedPoll.getQuestion());

    
    if (updatedPoll.getCandidates() != null) {
        for (Candidate updatedCandidate : updatedPoll.getCandidates()) {
            if (updatedCandidate.getId() != null) {
                
                Candidate existingCandidate = candidateRepository.findById(updatedCandidate.getId())
                        .orElseThrow(() -> new RuntimeException("Candidate not found"));
                existingCandidate.setName(updatedCandidate.getName());
                candidateRepository.save(existingCandidate);
            } else {
                
                updatedCandidate.setPoll(poll);
                candidateRepository.save(updatedCandidate);
            }
        }
    }

    return pollRepository.save(poll);
}
    public void deletePoll(Long id) {
        pollRepository.deleteById(id);
    }

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    public String castVote(Long candidateId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.isHasVoted()) {
            return "You have already voted!";
        }
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow(() -> new RuntimeException("Candidate not found"));
        candidate.setVotes(candidate.getVotes() + 1);
        candidateRepository.save(candidate);
        user.setHasVoted(true);
        userRepository.save(user);
        return "Vote cast successfully!";
    }

    public List<Candidate> getResults(Long pollId) {
        return candidateRepository.findByPollId(pollId);
    }
}