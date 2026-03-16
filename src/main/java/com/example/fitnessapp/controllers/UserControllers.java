package com.example.fitnessapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.fitnessapp.repository.UserRepository;
import com.example.fitnessapp.dto.UserProfileDTO;
import com.example.fitnessapp.entity.User;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/users")
public class UserControllers {

    private final UserRepository userRepository;

    public UserControllers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        
        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .map(user -> {
                    UserProfileDTO dto = new UserProfileDTO(
                        user.getActualUsername(),
                        user.getBio(),
                        user.getProfilePictureUrl(),
                        user.getGenderPreference()
                    );
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/me")
    public ResponseEntity<?> updateCurrentUser(@RequestBody UserProfileDTO dto, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .map(user -> {
                    user.setUsername(dto.getUsername());
                    user.setBio(dto.getBio());
                    user.setProfilePictureUrl(dto.getProfilePictureUrl());
                    user.setGenderPreference(dto.getGenderPreference());
                    userRepository.save(user);
                    return ResponseEntity.ok("Profile updated successfully");
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
