package com.example.fitnessapp.dto;



public class RoutineExercisesDTO {
    
    private Long exerciseId;
    private String exerciseName;
    private Integer orderIndex;
    private Integer defaultSets;
    private String primaryMuscleGroup;


    // Getters and Setters
    public Long getExerciseId() { return exerciseId; }
    public void setExerciseId(Long exerciseId) { this.exerciseId = exerciseId; }
    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }
    public Integer getOrderIndex() { return orderIndex; }
    public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
    public Integer getDefaultSets() { return defaultSets; }
    public void setDefaultSets(Integer defaultSets) { this.defaultSets = defaultSets; }
    public String getPrimaryMuscleGroup() { return primaryMuscleGroup; }
    public void setPrimaryMuscleGroup(String primaryMuscleGroup) { this.primaryMuscleGroup = primaryMuscleGroup; }

}
