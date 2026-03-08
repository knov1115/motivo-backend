package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.PlannedWorkoutDTO;
import com.example.fitnessapp.entity.PlannedWorkout;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.entity.WorkoutRoutine;
import com.example.fitnessapp.repository.PlannedWorkoutRepository;
import com.example.fitnessapp.repository.UserRepository;
import com.example.fitnessapp.repository.WorkoutRoutineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlannedWorkoutService {
    
    private final PlannedWorkoutRepository plannedWorkoutRepository;
    private final UserRepository userRepository;
    private final WorkoutRoutineRepository routineRepository;

    public PlannedWorkoutService(PlannedWorkoutRepository plannedWorkoutRepository, UserRepository userRepository, WorkoutRoutineRepository routineRepository) {
        this.plannedWorkoutRepository = plannedWorkoutRepository;
        this.userRepository = userRepository;
        this.routineRepository = routineRepository;
    }


    public PlannedWorkoutDTO scheduleWorkout(PlannedWorkoutDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        WorkoutRoutine routine = routineRepository.findById(dto.getRoutineId())
                .orElseThrow(() -> new RuntimeException("Routine not found"));

        PlannedWorkout plan = new PlannedWorkout();
        plan.setUser(user);
        plan.setRoutine(routine);
        plan.setDate(dto.getDate());

        PlannedWorkout savedPlan = plannedWorkoutRepository.save(plan);

        return convertToDTO(savedPlan);
    }


    public List<PlannedWorkoutDTO> getCalendarView(Long userId, LocalDate startDate, LocalDate endDate) {
        return plannedWorkoutRepository.findByUserIdAndDateBetween(userId, startDate, endDate)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public PlannedWorkoutDTO getTodaysWorkout(Long userId, LocalDate date) {
        return plannedWorkoutRepository.findByUserIdAndDate(userId, date)
                .map(this::convertToDTO)
                .orElse(null);
    }

    private PlannedWorkoutDTO convertToDTO(PlannedWorkout plan) {
        PlannedWorkoutDTO dto = new PlannedWorkoutDTO();
        dto.setId(plan.getId());
        dto.setUserId(plan.getUser().getId());
        dto.setRoutineId(plan.getRoutine().getId());
        dto.setRoutineName(plan.getRoutine().getName());
        dto.setDate(plan.getDate());
        return dto;
    }
}