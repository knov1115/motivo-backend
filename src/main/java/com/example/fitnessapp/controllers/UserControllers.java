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
                        user.getTargetWeight()
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
                    user.setUsername(dto.getUsername());
                    user.setBio(dto.getBio());
                    user.setProfilePictureUrl(dto.getProfilePictureUrl());
                    user.setGenderPreference(dto.getGenderPreference());
                    user.setCurrentWeight(dto.getCurrentWeight());
                    user.setTargetWeight(dto.getTargetWeight());

                    if (dto.getTargetWeight() != null) {
                        user.setTargetWeight(dto.getTargetWeight());
                    }

                    userRepository.save(user);
                    return ResponseEntity.ok("Profile updated successfully");
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
