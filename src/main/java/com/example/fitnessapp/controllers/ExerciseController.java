package com.example.fitnessapp.controllers;

import org.springframework.web.bind.annotation.*;
import com.example.fitnessapp.service.ExerciseService;
import com.example.fitnessapp.dto.ExerciseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
    
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseDTO>> getAllExercises() {
        List<ExerciseDTO> exercises = exerciseService.getAllActiveExercises();
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/muscle/{muscleGroup}")
    public ResponseEntity<List<ExerciseDTO>> getExercisesByMuscleGroup(@PathVariable String muscleGroup) {
        List<ExerciseDTO> exercises = exerciseService.getExercisesByMuscleGroup(muscleGroup);
        return ResponseEntity.ok(exercises);
    }

}
