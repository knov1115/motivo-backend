package com.example.fitnessapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.fitnessapp.entity.WorkoutSession;

import java.util.List;
import java.time.LocalDate;

@Repository
public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {
    
    // Get all workout sessions for a user, sorted by date descending
    List<WorkoutSession> findByUserIdOrderByDateDesc(Long userId);

    // Get all workout sessions for a user on a specific date (for the "Your workouts for today" screen)
    List<WorkoutSession> findByUserIdAndDate(Long userId, LocalDate date);

}
