package com.example.fitnessapp.controllers;

import com.example.fitnessapp.dto.PlannedWorkoutDTO;
import com.example.fitnessapp.service.PlannedWorkoutService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/planned-workouts")
public class PlannedWorkoutController {
    
    private final PlannedWorkoutService plannedWorkoutService;

    public PlannedWorkoutController(PlannedWorkoutService plannedWorkoutService) {
        this.plannedWorkoutService = plannedWorkoutService;
    }

    @PostMapping
    public ResponseEntity<PlannedWorkoutDTO> scheduleWorkout(@RequestBody PlannedWorkoutDTO dto) {
        return ResponseEntity.ok(plannedWorkoutService.scheduleWorkout(dto));
    }


    @GetMapping("/user/{userId}/calendar")
    public ResponseEntity<List<PlannedWorkoutDTO>> getCalendarView(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(plannedWorkoutService.getCalendarView(userId, startDate, endDate));
    }


    @GetMapping("/user/{userId}/today")
    public ResponseEntity<PlannedWorkoutDTO> getTodaysWorkout(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
            PlannedWorkoutDTO plan = plannedWorkoutService.getTodaysWorkout(userId, date);

            return plan != null ? ResponseEntity.ok(plan) : ResponseEntity.noContent().build();
    }

}
