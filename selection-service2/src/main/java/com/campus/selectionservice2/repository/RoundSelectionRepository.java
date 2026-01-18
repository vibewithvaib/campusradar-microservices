package com.campus.selectionservice2.repository;

import com.campus.selectionservice2.model.RoundSelection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoundSelectionRepository extends JpaRepository<RoundSelection, Long> {
    List<RoundSelection> findByStudentDriveId(Long id);
}

