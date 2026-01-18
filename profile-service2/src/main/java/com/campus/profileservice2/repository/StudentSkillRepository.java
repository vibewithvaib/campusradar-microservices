package com.campus.profileservice2.repository;

import com.campus.profileservice2.entity.StudentProfile;
import com.campus.profileservice2.entity.StudentSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentSkillRepository
        extends JpaRepository<StudentSkill, Long> {

    void deleteByStudent(StudentProfile student);
}
