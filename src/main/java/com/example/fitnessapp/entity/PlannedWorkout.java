package com.example.fitnessapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


import java.time.LocalDate;

@Entity
@Table(name = "planned_workouts", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "date"})
})
public class PlannedWorkout {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id", nullable = false)
    private WorkoutRoutine routine;
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();



    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public WorkoutRoutine getRoutine() { return routine; }
    public void setRoutine(WorkoutRoutine routine) { this.routine = routine; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
