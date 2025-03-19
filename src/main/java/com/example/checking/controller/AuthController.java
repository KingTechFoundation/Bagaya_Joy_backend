package com.example.checking.controller;

import com.example.checking.model.User;
import com.example.checking.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        try {
            userService.register(user);
            return "User registered successfully";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

 @PostMapping("/login")
public Map<String, Object> login(@RequestBody User user) {
    Map<String, Object> response = new HashMap<>();

    return userService.login(user.getEmail(), user.getPassword())
            .map(token -> {
                User loggedInUser = userService.findByEmail(user.getEmail()).orElse(null);

                if (loggedInUser != null) {
                    response.put("token", token);
                    response.put("userId", loggedInUser.getId()); // ✅ Make sure this line exists
                    response.put("username", loggedInUser.getUsername());
                    response.put("role", loggedInUser.getRole());
                    response.put("message", "Login successful");
                } else {
                    response.put("message", "User not found");
                }
                return response;
            })
            .orElseGet(() -> {
                response.put("message", "Invalid credentials");
                return response;
            });
}


}
