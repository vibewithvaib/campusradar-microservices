package com.campus.selectionservice2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class DriveSelection {

    @Id
    @GeneratedValue
    private Long id;

    private Long driveId;
    private String studentEmail;

    private int currentRound;
    private boolean active = true;
    private boolean selected = false;
    private boolean rejected = false;

    private LocalDateTime updatedAt;
}

