package com.campus.selectionservice2.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class RoundSelection {

    @Id
    @GeneratedValue
    private Long id;

    private Long studentDriveId;
    private Integer roundNumber;

    @Enumerated(EnumType.STRING)
    private SelectionStatus status;
}
