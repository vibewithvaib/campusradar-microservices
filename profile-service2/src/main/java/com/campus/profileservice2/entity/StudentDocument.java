package com.campus.profileservice2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class StudentDocument {
    @Id
    @GeneratedValue
    private Long id;

    private String type; // 10TH, 12TH, RESUME, EXPERIENCE, CERTIFICATE
    private String url;

    @ManyToOne
    private StudentProfile student;
}

