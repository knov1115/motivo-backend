package com.example.fitnessapp.dto;

import java.util.List;

public class WorkoutRoutineDTO {
    
    private Long id;
    private Long userId;
    private String name;
    private List<RoutineExercisesDTO> exercises;


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<RoutineExercisesDTO> getExercises() { return exercises; }
    public void setExercises(List<RoutineExercisesDTO> exercises) { this.exercises = exercises; }

}
