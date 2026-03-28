package com.example.fitnessapp.service;

import com.example.fitnessapp.dto.ExerciseSessionHistoryDTO;
import com.example.fitnessapp.dto.WorkoutSessionDTO;
import com.example.fitnessapp.dto.WorkoutSetDTO;
import com.example.fitnessapp.entity.*;
import com.example.fitnessapp.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;


@Service
public class WorkoutSessionService {
    
    private final WorkoutSessionRepository sessionRepository;
    private final WorkoutSetRepository setRepository;
    private final UserRepository userRepository;
    private final WorkoutRoutineRepository routineRepository;
    private final ExerciseRepository exerciseRepository;
    private final TrophyService trophyService;

    public WorkoutSessionService(WorkoutSessionRepository sessionRepository, WorkoutSetRepository setRepository, UserRepository userRepository, WorkoutRoutineRepository routineRepository, ExerciseRepository exerciseRepository, TrophyService trophyService) {
        this.sessionRepository = sessionRepository;
        this.setRepository = setRepository;
        this.userRepository = userRepository;
        this.routineRepository = routineRepository;
        this.exerciseRepository = exerciseRepository;
        this.trophyService = trophyService;
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


        WorkoutSessionDTO completedDto = getSessionById(savedSession.getId());
        trophyService.evaluateWorkoutTrophies(user, completedDto);

        // --- LEVEL UP LOGIC ---
        // Formula: 100 Base + (Duration * 5) + (Volume / 100)
        double totalVolume = 0.0;
        if (completedDto.getSets() != null) {
            for (WorkoutSetDTO set : completedDto.getSets()) {
                if (set.getWeight() != null && set.getReps() != null) {
                    totalVolume += (set.getWeight().doubleValue() * set.getReps());
                }
            }
        }

        int durationBonus = (dto.getDurationMinutes() != null ? dto.getDurationMinutes() : 0) * 5;
        int volumeBonus = (int) (totalVolume / 100); 
        int earnedExp = 100 + durationBonus + volumeBonus;

        int currentExp = user.getCurrentExp() + earnedExp;
        int currentLevel = user.getLevel();

        while (currentLevel < 100) {
            int requiredExpForNextLevel = calculateRequiredExp(currentLevel);
            if (currentExp >= requiredExpForNextLevel) {
                currentLevel++;
                currentExp -= requiredExpForNextLevel;
            } else {
                break;
            }
        }

        user.setLevel(currentLevel);
        user.setCurrentExp(currentExp);
        userRepository.save(user);

        return completedDto;



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
            setDto.setPrimaryMuscleGroup(set.getExercise().getPrimaryMuscleGroup());

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

    public ExerciseSessionHistoryDTO getLatestExerciseHistory(Long exerciseId, String userEmail) {


        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long latestSessionId = setRepository.findLatestSessionIdForExerciseAndUser(exerciseId, user.getId())
                .orElse(null);

        if (latestSessionId == null) {
            return null; // No history for this exercise
        }

        WorkoutSession session = sessionRepository.findById(latestSessionId)
                .orElseThrow(() -> new RuntimeException("Workout session not found"));

        List<WorkoutSet> previousSets = setRepository.findByWorkoutSessionIdAndExerciseIdOrderBySetIndexAsc(latestSessionId, exerciseId);

        if (previousSets.isEmpty()) {
            return null; // No sets found for this exercise in the session
        }

        ExerciseSessionHistoryDTO historyDTO = new ExerciseSessionHistoryDTO();
        historyDTO.setExerciseId(exerciseId);
        historyDTO.setExerciseName(previousSets.get(0).getExercise().getName());
        historyDTO.setPreviousWorkoutDate(session.getDate());

        List<WorkoutSetDTO> setDtos = previousSets.stream().map(set -> {
            WorkoutSetDTO setDto = new WorkoutSetDTO();
            setDto.setId(set.getId());
            setDto.setExerciseId(set.getExercise().getId());
            setDto.setExerciseName(set.getExercise().getName());
            setDto.setTag(set.getTag());
            setDto.setSetIndex(set.getSetIndex());
            setDto.setWeight(set.getWeight());
            setDto.setReps(set.getReps());
            setDto.setDurationSeconds(set.getDurationSeconds());
            setDto.setPrimaryMuscleGroup(set.getExercise().getPrimaryMuscleGroup());
            
            return setDto;
        }).collect(Collectors.toList());

        historyDTO.setSets(setDtos);
        return historyDTO;

    }

    private int calculateRequiredExp(int currentLevel) {
        return (int) (1000 * Math.pow(currentLevel, 1.2));
    }

}