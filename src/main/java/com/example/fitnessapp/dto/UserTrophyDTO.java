package com.example.fitnessapp.dto;

import com.example.fitnessapp.entity.enums.MetricType;
import java.time.LocalDateTime;

public class UserTrophyDTO {
    
    private Long trophyId;
    private String name;
    private String description;
    private MetricType metricType;
    private Double currentValue;
    private Double targetValue;
    private boolean isUnlocked;
    private LocalDateTime unlockedAt;
    private String iconFileName;


    public UserTrophyDTO() {}



    // Getters and Setters
    public Long getTrophyId() { return trophyId; }
    public void setTrophyId(Long trophyId) { this.trophyId = trophyId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public MetricType getMetricType() { return metricType; }
    public void setMetricType(MetricType metricType) { this.metricType = metricType; }

    public Double getTargetValue() { return targetValue; }
    public void setTargetValue(Double targetValue) { this.targetValue = targetValue; }

    public Double getCurrentValue() { return currentValue; }
    public void setCurrentValue(Double currentValue) { this.currentValue = currentValue; }

    public boolean isUnlocked() { return isUnlocked; }
    public void setUnlocked(boolean unlocked) { this.isUnlocked = unlocked; }

    public LocalDateTime getUnlockedAt() { return unlockedAt; }
    public void setUnlockedAt(LocalDateTime unlockedAt) { this.unlockedAt = unlockedAt; }

    public String getIconFileName() { return iconFileName; }
    public void setIconFileName(String iconFileName) { this.iconFileName = iconFileName; }

}
