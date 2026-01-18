package com.campus.selectionservice2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalPlacement {

    @Id
    @GeneratedValue
    private Long id;

    private String studentEmail;
    private Long driveId;
    private boolean accepted;
    private LocalDateTime placedAt = LocalDateTime.now();
}

