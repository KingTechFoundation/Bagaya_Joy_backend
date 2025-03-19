package com.example.checking.Services;

import com.example.checking.model.User;
import com.example.checking.repository.UserRepository;
import com.example.checking.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User register(User user) throws Exception {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new Exception("Email is required.");
        }
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception("Username already exists.");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already registered.");
        }

      
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

   
    public Optional<String> login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);  
        if (userOpt.isPresent() && encoder.matches(password, userOpt.get().getPassword())) {
            String token = jwtUtil.generateToken(userOpt.get().getEmail(), userOpt.get().getRole());  
            return Optional.of(token);
        }
        return Optional.empty();
    }

    public void markAsVoted(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setHasVoted(true);
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
