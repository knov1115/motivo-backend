package com.example.fitnessapp.controllers;

import org.springframework.web.bind.annotation.*;
import com.example.fitnessapp.service.WorkoutSessionService;
import com.example.fitnessapp.dto.WorkoutSessionDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class WorkoutSessionController {
    
    private final WorkoutSessionService sessionService;

    public WorkoutSessionController(WorkoutSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<WorkoutSessionDTO> logSession(@RequestBody WorkoutSessionDTO dto) {
        return ResponseEntity.ok(sessionService.logWorkoutSession(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutSessionDTO> getSession(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.getSessionById(id));
    }

    @GetMapping("/user/{userId}/history")
    public ResponseEntity<List<WorkoutSessionDTO>> getUserHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(sessionService.getUserHistory(userId));
    }

}
