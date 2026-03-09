package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.WorkoutSessionDTO;
import com.example.fitnessapp.dto.WorkoutSetDTO;
import com.example.fitnessapp.entity.*;
import com.example.fitnessapp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class WorkoutSessionService {
    
    private final WorkoutSessionRepository sessionRepository;
    private final WorkoutSetRepository setRepository;
    private final UserRepository userRepository;
    private final WorkoutRoutineRepository routineRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutSessionService(WorkoutSessionRepository sessionRepository, WorkoutSetRepository setRepository, UserRepository userRepository, WorkoutRoutineRepository routineRepository, ExerciseRepository exerciseRepository) {
        this.sessionRepository = sessionRepository;
        this.setRepository = setRepository;
        this.userRepository = userRepository;
        this.routineRepository = routineRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @Transactional
    public WorkoutSessionDTO logWorkoutSession(WorkoutSessionDTO dto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        WorkoutSession session = new WorkoutSession();
        session.setUser(user);
        session.setDate(dto.getDate() != null ? dto.getDate() : LocalDate.now());
        session.setDurationMinutes(dto.getDurationMinutes());
        session.setNotes(dto.getNotes());
        
        if (dto.getRoutineId() != null) {
            WorkoutRoutine routine = routineRepository.findById(dto.getRoutineId())
                    .orElse(null);
            session.setRoutine(routine);
        }

        WorkoutSession savedSession = sessionRepository.save(session);

        if(dto.getSets() != null) {
            for (WorkoutSetDTO setDto : dto.getSets()) {
                Exercise exercise = exerciseRepository.findById(setDto.getExerciseId())
                        .orElseThrow(() -> new RuntimeException("Exercise not found"));
                
                WorkoutSet workoutSet = new WorkoutSet();
                workoutSet.setWorkoutSession(savedSession);
                workoutSet.setExercise(exercise);
                workoutSet.setSetIndex(setDto.getSetIndex());
                workoutSet.setWeight(setDto.getWeight());
                workoutSet.setReps(setDto.getReps());
                workoutSet.setDurationSeconds(setDto.getDurationSeconds());
                workoutSet.setTag(setDto.getTag() != null ? setDto.getTag() : "WORKING");

                setRepository.save(workoutSet);
            }
        }

        return getSessionById(savedSession.getId());



    }

    public WorkoutSessionDTO getSessionById(Long sessionId) {
        WorkoutSession session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Workout session not found"));

        List<WorkoutSet> sets = setRepository.findByWorkoutSessionIdOrderByExerciseIdAscSetIndexAsc(sessionId);

        WorkoutSessionDTO dto = new WorkoutSessionDTO();
        dto.setId(session.getId());
        dto.setUserId(session.getUser().getId());
        dto.setDate(session.getDate());
        dto.setDurationMinutes(session.getDurationMinutes());
        dto.setNotes(session.getNotes());
        if (session.getRoutine() != null) {
            dto.setRoutineId(session.getRoutine().getId());
        }

        List<WorkoutSetDTO> setDtos = sets.stream().map(set -> {
            WorkoutSetDTO setDto = new WorkoutSetDTO();
            setDto.setId(set.getId());
            setDto.setExerciseId(set.getExercise().getId());
            setDto.setExerciseName(set.getExercise().getName());
            setDto.setTag(set.getTag());
            setDto.setSetIndex(set.getSetIndex());
            setDto.setWeight(set.getWeight());
            setDto.setReps(set.getReps());
            setDto.setDurationSeconds(set.getDurationSeconds());
            return setDto;
        }).collect(Collectors.toList());

        dto.setSets(setDtos);
        return dto;
    }

    public List<WorkoutSessionDTO> getUserHistory(Long userId) {
        return sessionRepository.findByUserIdOrderByDateDesc(userId)
                .stream()
                .map(session -> getSessionById(session.getId()))
                .collect(Collectors.toList());
    }

    public List<WorkoutSessionDTO> getMyHistory(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return sessionRepository.findByUserIdOrderByDateDesc(user.getId())
                .stream()
                .map(session -> getSessionById(session.getId()))
                .collect(Collectors.toList());
    }
}