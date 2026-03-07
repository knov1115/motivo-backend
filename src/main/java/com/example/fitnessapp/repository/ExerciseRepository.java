package com.example.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.fitnessapp.entity.Exercise;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    
    List<Exercise> findByIsActiveTrue();

    List<Exercise> findByPrimaryMuscleGroupAndIsActiveTrue(String primaryMuscleGroup);

    List<Exercise> findByNameContainingIgnoreCaseAndIsActiveTrue(String keyword);

}
