package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.RoutineExercisesDTO;
import com.example.fitnessapp.dto.WorkoutRoutineDTO;
import com.example.fitnessapp.entity.Exercise;
import com.example.fitnessapp.entity.RoutineExercise;
import com.example.fitnessapp.entity.User;
import com.example.fitnessapp.entity.WorkoutRoutine;
import com.example.fitnessapp.repository.ExerciseRepository;
import com.example.fitnessapp.repository.RoutineExerciseRepository;
import com.example.fitnessapp.repository.UserRepository;
import com.example.fitnessapp.repository.WorkoutRoutineRepository;
import com.example.fitnessapp.repository.WorkoutSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutRoutineService {
    
    private final WorkoutRoutineRepository routineRepository;
    private final RoutineExerciseRepository routineExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public WorkoutRoutineService(WorkoutRoutineRepository routineRepository, RoutineExerciseRepository routineExerciseRepository, ExerciseRepository exerciseRepository, UserRepository userRepository) {
        this.routineRepository = routineRepository;
        this.routineExerciseRepository = routineExerciseRepository;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public WorkoutRoutineDTO createRoutine(WorkoutRoutineDTO dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        WorkoutRoutine routine = new WorkoutRoutine();
        routine.setName(dto.getName());
        routine.setUser(user);
        WorkoutRoutine savedRoutine = routineRepository.save(routine);

        if (dto.getExercises() != null) {
            for (RoutineExercisesDTO exDto: dto.getExercises()) {
                Exercise exercise = exerciseRepository.findById(exDto.getExerciseId())
                        .orElseThrow(() -> new RuntimeException("Exercise not found"));

                RoutineExercise routineExercise = new RoutineExercise();
                routineExercise.setRoutine(savedRoutine);
                routineExercise.setExercise(exercise);
                routineExercise.setOrderIndex(exDto.getOrderIndex());
                routineExercise.setDefaultSets(exDto.getDefaultSets());

                routineExerciseRepository.save(routineExercise);
            }
        }
        return getRoutineById(savedRoutine.getId());
    }


    public WorkoutRoutineDTO getRoutineById(Long routineId) {
        WorkoutRoutine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new RuntimeException("Routine not found"));

        List<RoutineExercise> exercises = routineExerciseRepository.findByRoutineIdOrderByOrderIndexAsc(routineId);

        WorkoutRoutineDTO dto = new WorkoutRoutineDTO();
        dto.setId(routine.getId());
        dto.setName(routine.getName());
        dto.setUserId(routine.getUser().getId());

        List<RoutineExercisesDTO> exDtos = exercises.stream().map(ex -> {
            RoutineExercisesDTO exDto = new RoutineExercisesDTO();
            exDto.setExerciseId(ex.getExercise().getId());
            exDto.setExerciseName(ex.getExercise().getName());
            exDto.setOrderIndex(ex.getOrderIndex());
            exDto.setDefaultSets(ex.getDefaultSets());
            return exDto;
        }).collect(Collectors.toList());

        dto.setExercises(exDtos);
        return dto;
    }

    public List<WorkoutRoutineDTO> getRoutinesByUser(Long userId) {
        return routineRepository.findByUserId(userId)
                .stream()
                .map(routine -> getRoutineById(routine.getId()))
                .collect(Collectors.toList());
    }

    public List<WorkoutRoutineDTO> getMyRoutines(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return routineRepository.findByUserId(user.getId())
                .stream()
                .map(routine -> getRoutineById(routine.getId()))
                .collect(Collectors.toList());
    }

}

