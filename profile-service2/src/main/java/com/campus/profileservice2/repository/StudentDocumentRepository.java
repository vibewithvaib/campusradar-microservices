package com.campus.profileservice2.repository;

import com.campus.profileservice2.entity.StudentDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDocumentRepository
        extends JpaRepository<StudentDocument, Long> {
}

