package com.example.fitnessapp.controllers;

import org.springframework.web.bind.annotation.*;
import com.example.fitnessapp.service.WorkoutRoutineService;
import com.example.fitnessapp.dto.WorkoutRoutineDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/routines")
public class WorkoutRoutineController {

    private final WorkoutRoutineService routineService;

    public WorkoutRoutineController(WorkoutRoutineService routineService) {
        this.routineService = routineService;
    }

    @PostMapping
    public ResponseEntity<WorkoutRoutineDTO> createRoutine(@RequestBody WorkoutRoutineDTO dto) {
        return ResponseEntity.ok(routineService.createRoutine(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutRoutineDTO> getRoutine(@PathVariable Long id) {
        return ResponseEntity.ok(routineService.getRoutineById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkoutRoutineDTO>> getUserRoutines(@PathVariable Long userId) {
        return ResponseEntity.ok(routineService.getRoutinesByUser(userId));
    }
    
}
