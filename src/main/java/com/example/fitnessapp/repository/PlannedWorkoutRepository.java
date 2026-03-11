package com.example.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.fitnessapp.entity.PlannedWorkout;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface PlannedWorkoutRepository extends JpaRepository<PlannedWorkout, Long> {
    
    // Get a specific day's plan (for the "Your workouts for today" screen)
    Optional<PlannedWorkout> findByUserIdAndDate(Long userId, LocalDate date);

    // Get the whole month's plan (for the Calendar view)
    List<PlannedWorkout> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

    List<PlannedWorkout> findByRoutineId(Long routineId);

}
