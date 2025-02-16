package com.culture.association_backend.controller;

import com.culture.association_backend.repository.UserRepository;
import com.culture.association_backend.security.JwtUtil;
import com.culture.association_backend.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {  // ✅ Class name corrected

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ✅ Constructor name corrected to match the class
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> credentials) {
        User user = (User) userRepository.findByUsername(credentials.get("username"))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(credentials.get("password"), user.getPassword())) {
            return jwtUtil.generateToken(user.getUsername());
        } else {
            return "Invalid credentials";
        }
    }
}
