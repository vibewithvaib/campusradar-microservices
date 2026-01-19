package com.campus.profileservice2.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class StudentSkill {

    @Id
    @GeneratedValue
    private Long id;

    private String skill;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentProfile student;
}


