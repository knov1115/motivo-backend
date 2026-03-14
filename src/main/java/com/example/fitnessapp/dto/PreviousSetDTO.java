package com.example.fitnessapp.dto;

public class PreviousSetDTO {
    
    private Integer setNumber;
    private Double weight;
    private Integer reps;
    private String tag;

    // Getters and setters
    public Integer getSetNumber() {
        return setNumber;
    }
    public void setSetNumber(Integer setNumber) {
        this.setNumber = setNumber;
    }
    public Double getWeight() {
        return weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    public Integer getReps() {
        return reps;
    }
    public void setReps(Integer reps) {
        this.reps = reps;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    

}
