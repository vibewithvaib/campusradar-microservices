package com.campus.profileservice2.dto;

import com.campus.profileservice2.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StudentProfileResponseDto {
    private StudentProfile profile;
    private StudentAcademics academics;
    private List<StudentSkill> skills;
    private List<StudentExperience> experiences;
    private List<StudentDocument> documents;
}

