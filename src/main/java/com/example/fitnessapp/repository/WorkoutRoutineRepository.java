package com.example.fitnessapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.fitnessapp.entity.WorkoutRoutine;

@Repository
public interface WorkoutRoutineRepository extends JpaRepository<WorkoutRoutine, Long> {
    
    // Fetch all custom routines for a specific user
    List<WorkoutRoutine> findByUserId(Long userId);

}
