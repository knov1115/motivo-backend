package com.example.fitnessapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.example.fitnessapp.repository.UserRepository;
import com.example.fitnessapp.dto.UserProfileDTO;
import com.example.fitnessapp.entity.User;
import org.springframework.web.bind.annotation.*;
import com.example.fitnessapp.dto.UserTrophyDTO;
import com.example.fitnessapp.service.TrophyService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserControllers {

    private final UserRepository userRepository;
    private final TrophyService trophyService;

    public UserControllers(UserRepository userRepository, TrophyService trophyService) {
        this.userRepository = userRepository;
        this.trophyService = trophyService;
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
                        user.getGenderPreference(),
                        user.getCurrentWeight(),
                        user.getTargetWeight(),
                        user.getLevel(),
                        user.getCurrentExp()
                    );
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/me/trophies")
    public ResponseEntity<List<UserTrophyDTO>> getMyTrophies(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<UserTrophyDTO> trophies = trophyService.getUserTrophiesAsDTO(user.getId());

        return ResponseEntity.ok(trophies);
    } 


    @PutMapping("/me")
    public ResponseEntity<?> updateCurrentUser(@RequestBody UserProfileDTO dto, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .map(user -> {
                    // Only update text fields if they are actually provided
                    if (dto.getUsername() != null) user.setUsername(dto.getUsername());
                    if (dto.getBio() != null) user.setBio(dto.getBio());
                    if (dto.getProfilePictureUrl() != null) user.setProfilePictureUrl(dto.getProfilePictureUrl());
                    if (dto.getGenderPreference() != null) user.setGenderPreference(dto.getGenderPreference());

                
                    if (dto.getCurrentWeight() != null) {
                        user.setCurrentWeight(dto.getCurrentWeight());
                    }
                    if (dto.getTargetWeight() != null) {
                        user.setTargetWeight(dto.getTargetWeight());
                    }

                    userRepository.save(user);
                    return ResponseEntity.ok("Profile updated successfully");
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
