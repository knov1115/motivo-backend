package com.example.fitnessapp.dto;

public class ExerciseDTO {
    
    private Long id;
    private String name;
    private String primaryMuscleGroup;
    private String equipmentType;
    private String description;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPrimaryMuscleGroup() { return primaryMuscleGroup; }
    public void setPrimaryMuscleGroup(String primaryMuscleGroup) { this.primaryMuscleGroup = primaryMuscleGroup; }
    public String getEquipmentType() { return equipmentType; }
    public void setEquipmentType(String equipmentType) { this.equipmentType = equipmentType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

}
