package com.example.fitnessapp.service.seeder;

import com.example.fitnessapp.entity.Exercise;
import com.example.fitnessapp.repository.ExerciseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class ExerciseDataSeeder implements CommandLineRunner {

    private final ExerciseRepository exerciseRepository;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String GITHUB_DATA_URL = "https://raw.githubusercontent.com/yuhonas/free-exercise-db/main/dist/exercises.json";

    public ExerciseDataSeeder(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (exerciseRepository.count() == 0) {
            System.out.println("Seeding exercise data from JSON file");

            try {
                RestTemplate restTemplate = new RestTemplate();
                String jsonContent = restTemplate.getForObject(GITHUB_DATA_URL, String.class);

                List<GithubExerciseDTO> importedExercises = objectMapper.readValue(jsonContent, new TypeReference<List<GithubExerciseDTO>>() {});

                List<Exercise> exercisesToSave = importedExercises.stream().map(dto -> {
                    Exercise exercise = new Exercise();
                    exercise.setName(dto.getName());

                    String equipment = dto.getEquipment();
                    exercise.setEquipment(equipment != null ? capitalize(equipment) : "Bodyweight");

                    if (dto.getPrimaryMuscles() != null && !dto.getPrimaryMuscles().isEmpty()) {
                        exercise.setPrimaryMuscleGroup(capitalize(dto.getPrimaryMuscles().get(0)));
                    } else {
                        exercise.setPrimaryMuscleGroup("Full body");
                    }

                    String combinedDescription = "";
                    if (dto.getInstructions() != null && !dto.getInstructions().isEmpty()) {
                        combinedDescription = String.join(" ", dto.getInstructions());
                    }
                    exercise.setDescription(combinedDescription);

                    exercise.setIsActive(true);
                    return exercise;

                }).collect(Collectors.toList());

                exerciseRepository.saveAll(exercisesToSave);
                System.out.println("Successfully seeded " + exercisesToSave.size() + " exercises.");
            } catch (Exception e) {
                System.err.println("Error occurred while seeding exercise data: " + e.getMessage());
            } 
        } else {
            System.out.println("Exercise data already exists. Skipping seeding.");
        }
    }


    // helper to capitalize first letter
    private String capitalize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }


    // helper DTO for importing exercises from JSON
    private static class GithubExerciseDTO {
        private String name;
        private String equipment;
        private List<String> primaryMuscles;
        private List<String> instructions;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getEquipment() { return equipment; }
        public void setEquipment(String equipment) { this.equipment = equipment; }
        public List<String> getPrimaryMuscles() { return primaryMuscles; }
        public void setPrimaryMuscles(List<String> primaryMuscles) { this.primaryMuscles = primaryMuscles; }
        public List<String> getInstructions() { return instructions; }
        public void setInstructions(List<String> instructions) { this.instructions = instructions; }
    }
    

}
