package com.example.fitnessapp.entity;

import jakarta.persistence.*;
import com.example.fitnessapp.entity.enums.MetricType;


@Entity
@Table(name = "trophies")
public class Trophy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "metric_type", nullable = false)
    private MetricType metricType;

    @Column(name = "target_value", nullable = false)
    private Double targetValue;

    @Column(name = "icon_svg", columnDefinition = "TEXT")
    private String iconSvg;

    

    public Trophy() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public MetricType getMetricType() { return metricType; }
    public void setMetricType(MetricType metricType) { this.metricType = metricType; }

    public Double getTargetValue() { return targetValue; }
    public void setTargetValue(Double targetValue) { this.targetValue = targetValue; }

    public String getIconSvg() { return iconSvg; }
    public void setIconSvg(String iconSvg) { this.iconSvg = iconSvg; }

}
