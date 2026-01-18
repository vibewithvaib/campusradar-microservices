package com.campus.selectionservice2.repository;

import com.campus.selectionservice2.model.StudentDrive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentDriveRepository extends JpaRepository<StudentDrive, Long> {
    List<StudentDrive> findByStudentEmail(String email);
}

