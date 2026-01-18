package com.campus.profileservice2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class StudentProfile {

    @Id @GeneratedValue
    private Long id;

    private String email;
    private String fullName;
    private String branch;
    private String college;

    private boolean verified = false;
    private boolean blacklisted = false;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private StudentAcademics academics;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentSkill> skills;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentExperience> experiences;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentDocument> documents;
}


