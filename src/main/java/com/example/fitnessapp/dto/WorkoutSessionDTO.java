package com.example.fitnessapp.dto;

import java.time.LocalDate;
import java.util.List;

public class WorkoutSessionDTO {
    
    private Long id;
    private Long userId;
    private Long routineId;
    private Long plannedWorkoutId;
    private LocalDate date;
    private Integer durationMinutes;
    private String notes;
    private List<WorkoutSetDTO> sets;



    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getRoutineId() { return routineId; }
    public void setRoutineId(Long routineId) { this.routineId = routineId; }
    public Long getPlannedWorkoutId() { return plannedWorkoutId; }
    public void setPlannedWorkoutId(Long plannedWorkoutId) { this.plannedWorkoutId = plannedWorkoutId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public List<WorkoutSetDTO> getSets() { return sets; }
    public void setSets(List<WorkoutSetDTO> sets) { this.sets = sets; }
}
