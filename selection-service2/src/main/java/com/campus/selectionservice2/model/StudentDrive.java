package com.campus.selectionservice2.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class StudentDrive {

    @Id
    @GeneratedValue
    private Long id;

    private String studentEmail;
    private Long driveId;

    private Integer currentRound = 1;

    @Enumerated(EnumType.STRING)
    private SelectionStatus status = SelectionStatus.INVITED;
}
