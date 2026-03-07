package com.example.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.fitnessapp.entity.WorkoutSet;

import java.util.List;

@Repository
public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, Long> {
    
    // Fetch all sets logged in a specific session, ordered logically
    List<WorkoutSet> findByWorkoutSessionIdOrderByExerciseIdAscSetIndexAsc(Long sessionId);

    //Get historical sets for a specific user and exercise
    List<WorkoutSet> findByWorkoutSessionUserIdAndExerciseIdOrderByWorkoutSessionDateDesc(Long userId, Long exerciseId);

}
