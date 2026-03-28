package com.example.fitnessapp.dto;

import com.example.fitnessapp.entity.User;
import java.math.BigDecimal;

public class UserProfileDTO {
    
    private String username;
    private String bio;
    private String profilePictureUrl;
    private String genderPreference;
    private BigDecimal currentWeight;
    private BigDecimal targetWeight;

    public UserProfileDTO() {
    }

    public UserProfileDTO(String username, String bio, String profilePictureUrl, String genderPreference, BigDecimal currentWeight, BigDecimal targetWeight) {
        this.username = username;
        this.bio = bio;
        this.profilePictureUrl = profilePictureUrl;
        this.genderPreference = genderPreference;
        this.currentWeight = currentWeight;
        this.targetWeight = targetWeight;
    }


    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getGenderPreference() {
        return genderPreference;
    }

    public void setGenderPreference(String genderPreference) {
        this.genderPreference = genderPreference;
    }

    public BigDecimal getCurrentWeight() { return currentWeight; }
    public void setCurrentWeight(BigDecimal currentWeight) { this.currentWeight = currentWeight; }
    
    public BigDecimal getTargetWeight() { return targetWeight; }
    public void setTargetWeight(BigDecimal targetWeight) { this.targetWeight = targetWeight; }
}
