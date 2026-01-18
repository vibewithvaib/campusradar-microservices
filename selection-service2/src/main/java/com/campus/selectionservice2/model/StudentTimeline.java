package com.campus.selectionservice2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class StudentTimeline {

    @Id
    @GeneratedValue
    private Long id;

    private String studentEmail;
    private Long driveId;
    private String action;
    private LocalDateTime time = LocalDateTime.now();
}

