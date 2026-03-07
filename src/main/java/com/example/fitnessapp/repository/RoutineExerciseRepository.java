package com.example.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.fitnessapp.entity.RoutineExercise;

import java.util.List;

public interface RoutineExerciseRepository extends JpaRepository<RoutineExercise, Long> {
    
    // Get all exercises for a routine, automatically sorted by order_index
    List<RoutineExercise> findByRoutineIdOrderByOrderIndexAsc(Long routineId);

}
