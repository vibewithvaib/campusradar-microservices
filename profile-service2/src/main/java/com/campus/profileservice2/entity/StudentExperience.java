package com.campus.profileservice2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class StudentExperience {
    @Id
    @GeneratedValue
    private Long id;

    private String company;
    private String role;
    private String duration;
    private String description;

    @ManyToOne
    private StudentProfile student;
}

