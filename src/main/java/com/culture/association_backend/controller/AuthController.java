package com.culture.association_backend.controller;

import com.culture.association_backend.model.Role;
import com.culture.association_backend.model.RoleName;
import com.culture.association_backend.repository.UserRepository;
import com.culture.association_backend.security.JwtUtil;
import com.culture.association_backend.model.User;
import com.culture.association_backend.service.TokenBlacklistService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {  // ✅ Class name corrected
    private final TokenBlacklistService tokenBlacklistService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ✅ Constructor name corrected to match the class
    public AuthController(TokenBlacklistService tokenBlacklistService, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.tokenBlacklistService = tokenBlacklistService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate session for session-based authentication
        request.getSession().invalidate();

        // Remove authentication cookies
        response.setHeader("Set-Cookie", "JSESSIONID=; HttpOnly; Path=/; Max-Age=0");

        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        Set<Role> roles = new HashSet<>();
        Role role = new Role();
        role.setName(String.valueOf(RoleName.ROLE_ADMIN));
        roles.add(role);

        user.setUsername(user.getUsername());

        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> data) {
        System.out.println(("credential" + data));
        // Fetch the user from the database based on username
        User user = (User) userRepository.findByUsername(data.get("username"))
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("body: " + data);  // Log credentials for debugging
        Map<String, Object> response = new HashMap<>();
        // Validate the password with the passwordEncoder
        if (passwordEncoder.matches(data.get("password"), user.getPassword())) {
            if (user.getIs_active().equals("1")) {

                response.put("token", jwtUtil.generateToken(user.getUsername()));
                response.put("username", user.getUsername());
                return ResponseEntity.ok(response);

            } else {

                response.put("error", "user has not been activated");

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                // If credentials are invalid, return 401 Unauthorized with an error message
                // return ResponseEntity.status(401).body(Map.of("error", "user has not been activated"));
            }


        } else {
            response.put("error", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            // If credentials are invalid, return 401 Unauthorized with an error message
            // return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }

    }
}

