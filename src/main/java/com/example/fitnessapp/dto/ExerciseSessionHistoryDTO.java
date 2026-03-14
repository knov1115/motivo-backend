package com.example.fitnessapp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.example.fitnessapp.dto.WorkoutSetDTO;

public class ExerciseSessionHistoryDTO {
    
    private Long exerciseId;
    private String exerciseName;
    private LocalDate previousWorkoutDate;
    private List<WorkoutSetDTO> sets;

    // Getters and Setters
    public Long getExerciseId() { return exerciseId; }
    public void setExerciseId(Long exerciseId) { this.exerciseId = exerciseId; }

    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }

    public LocalDate getPreviousWorkoutDate() { return previousWorkoutDate; }
    public void setPreviousWorkoutDate(LocalDate previousWorkoutDate) { this.previousWorkoutDate = previousWorkoutDate; }

    public List<WorkoutSetDTO> getSets() { return sets; }
    public void setSets(List<WorkoutSetDTO> sets) { this.sets = sets; }


}
