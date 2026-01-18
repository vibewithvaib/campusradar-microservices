package com.campus.profileservice2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class StudentAcademics {
    @Id
    @GeneratedValue
    private Long id;

    private Double tenthMarks;
    private Double twelfthMarks;
    private Double cgpa;

    @OneToOne
    private StudentProfile student;
}

