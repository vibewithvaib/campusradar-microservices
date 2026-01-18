package com.campus.selectionservice2.repository;

import com.campus.selectionservice2.model.StudentTimeline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentTimelineRepository extends JpaRepository<StudentTimeline, Long> {
    List<StudentTimeline> findByStudentEmail(String email);
}
