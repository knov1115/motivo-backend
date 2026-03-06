package com.example.fitnessapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserControllers {
    
    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.ok("Hello, " + authentication.getName() + "! This is your profile.");
    }

}
