package com.campus.profileservice2.repository;

import com.campus.profileservice2.entity.StudentExperience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentExperienceRepository
        extends JpaRepository<StudentExperience, Long> {
}

