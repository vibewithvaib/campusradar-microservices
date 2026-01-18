package com.campus.profileservice2.repository;

import com.campus.profileservice2.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StudentProfileRepository
        extends JpaRepository<StudentProfile, Long> {

    Optional<StudentProfile> findByEmail(String email);

    List<StudentProfile> findByVerifiedTrueAndBlacklistedFalse();
}

