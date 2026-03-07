package com.example.fitnessapp.service;

import org.springframework.stereotype.Service;
import com.example.fitnessapp.repository.ExerciseRepository;
import com.example.fitnessapp.dto.ExerciseDTO;
import com.example.fitnessapp.entity.Exercise;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseService {  
    
    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }



    // Get all active exercises
    public List<ExerciseDTO> getAllActiveExercises() {
        return exerciseRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<ExerciseDTO> getExercisesByMuscleGroup(String muscleGroup) {
        return exerciseRepository.findByPrimaryMuscleGroupAndIsActiveTrue(muscleGroup)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    
    // helper to transform entity into DTO
    private ExerciseDTO convertToDTO(Exercise exercise) {
        ExerciseDTO dto = new ExerciseDTO();
        dto.setId(exercise.getId());
        dto.setName(exercise.getName());
        dto.setPrimaryMuscleGroup(exercise.getPrimaryMuscleGroup());
        dto.setEquipmentType(exercise.getEquipment());
        dto.setDescription(exercise.getDescription());
        return dto;
    }

}
