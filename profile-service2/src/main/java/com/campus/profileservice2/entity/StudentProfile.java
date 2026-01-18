package com.campus.profileservice2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class StudentProfile {
    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String fullName;
    private String branch;
    private String college;

    private boolean verified = false;
    private boolean blacklisted = false;
}

