package com.example.fitnessapp.dto;

import java.time.LocalDate;
import java.math.BigDecimal;

public class BodyWeightEntryDTO {
    
    private Long id;
    private Long userId;
    private LocalDate date;
    private BigDecimal weight;

    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

}
