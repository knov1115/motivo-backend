package com.example.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.fitnessapp.entity.WorkoutSet;

import java.util.Optional;
import java.util.List;

@Repository
public interface WorkoutSetRepository extends JpaRepository<WorkoutSet, Long> {
    
    // Fetch all sets logged in a specific session, ordered logically
    List<WorkoutSet> findByWorkoutSessionIdOrderByExerciseIdAscSetIndexAsc(Long sessionId);

    // find the most recent session ID where this user performed this exercise
    @Query(value = "SELECT ws.workoutSession.id FROM WorkoutSet ws " +
                   "WHERE ws.workoutSession.user.id = :userId AND ws.exercise.id = :exerciseId " +
                   "ORDER BY ws.workoutSession.date DESC, ws.workoutSession.id DESC LIMIT 1")
    Optional<Long> findLatestSessionIdForExerciseAndUser(@Param("exerciseId") Long exerciseId, @Param("userId") Long userId);

    //Get historical sets for a specific user and exercise
    List<WorkoutSet> findByWorkoutSessionUserIdAndExerciseIdOrderByWorkoutSessionDateDesc(Long userId, Long exerciseId);


    List<WorkoutSet> findByWorkoutSessionIdAndExerciseIdOrderBySetIndexAsc(Long sessionId, Long exerciseId);
}
