package com.campus.driveservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class DriveCriteria {

    @Id
    @GeneratedValue
    private Long id;

    private Double minTenth;
    private Double minTwelfth;
    private String requiredSkills; // comma separated

    @OneToOne
    private Drive drive;
}

