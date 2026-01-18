package com.campus.profileservice2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class StudentSkill {
    @Id
    @GeneratedValue
    private Long id;
    private String skill;

    @ManyToOne
    private StudentProfile student;
}

