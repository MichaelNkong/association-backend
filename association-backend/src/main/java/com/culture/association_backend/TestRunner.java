package com.culture.association_backend;

import com.culture.association_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestRunner implements CommandLineRunner {

    @Autowired
    private AuthService authService; // Inject AuthService

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Running authentication test...");

        try {
            authService.authenticateUser("michael", "HoMoZea4722%?");
        } catch (Exception e) {
            System.err.println("Authentication failed: " + e.getMessage());
        }
    }
}