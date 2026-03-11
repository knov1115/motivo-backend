package com.example.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.fitnessapp.entity.Exercise;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    
     // Finds all exercises where is_active = true
    List<Exercise> findByIsActiveTrue();

    // Filters for the frontend UI
    List<Exercise> findByPrimaryMuscleGroupAndIsActiveTrue(String primaryMuscleGroup);

    // Search bar functionality
    List<Exercise> findByNameContainingIgnoreCaseAndIsActiveTrue(String keyword);

}
