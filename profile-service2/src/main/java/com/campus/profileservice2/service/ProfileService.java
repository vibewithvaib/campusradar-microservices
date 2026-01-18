package com.campus.profileservice2.service;

import com.campus.profileservice2.dto.*;
import com.campus.profileservice2.entity.*;
import com.campus.profileservice2.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {

    private final StudentProfileRepository profileRepo;
    private final StudentAcademicsRepository academicsRepo;
    private final StudentSkillRepository skillRepo;
    private final StudentDocumentRepository docRepo;
    private final StudentExperienceRepository expRepo;
    private final RecruiterProfileRepository recruiterRepo;
    public void saveStudentProfile(String email, StudentProfileRequestDto dto) {

        StudentProfile profile = profileRepo
                .findByEmail(email)
                .orElse(new StudentProfile());

        profile.setEmail(email);
        profile.setFullName(dto.getFullName());
        profile.setBranch(dto.getBranch());
        profile.setCollege(dto.getCollege());
        profileRepo.save(profile);

        StudentAcademics ac = new StudentAcademics();
        ac.setStudent(profile);
        ac.setTenthMarks(dto.getTenthMarks());
        ac.setTwelfthMarks(dto.getTwelfthMarks());
        ac.setCgpa(dto.getCgpa());
        academicsRepo.save(ac);

        skillRepo.deleteByStudent(profile);
        for (String s : dto.getSkills()) {
            StudentSkill skill = new StudentSkill();
            skill.setSkill(s);
            skill.setStudent(profile);
            skillRepo.save(skill);
        }

        for (StudentDocumentDto d : dto.getDocuments()) {
            StudentDocument doc = new StudentDocument();
            doc.setStudent(profile);
            doc.setType(d.getType());
            doc.setUrl(d.getUrl());
            docRepo.save(doc);
        }

        for (StudentExperienceDto e : dto.getExperiences()) {
            StudentExperience ex = new StudentExperience();
            ex.setStudent(profile);
            ex.setCompany(e.getCompany());
            ex.setRole(e.getRole());
            ex.setDuration(e.getDuration());
            ex.setDescription(e.getDescription());
            expRepo.save(ex);
        }
    }

    public void verifyStudent(Long studentId) {
        StudentProfile p = profileRepo.findById(studentId).orElseThrow();
        p.setVerified(true);
        profileRepo.save(p);
    }
    public void createRecruiterProfile(RecruiterProfileRequestDto dto) {
        RecruiterProfile rp = new RecruiterProfile();
        rp.setEmail(dto.getEmail());
        rp.setCompanyName(dto.getCompanyName());
        rp.setDesignation(dto.getDesignation());
        rp.setDescription(dto.getDescription());
        recruiterRepo.save(rp);
    }

    public void blacklistStudentByEmail(String email) {
        StudentProfile p = profileRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        p.setBlacklisted(true);
        profileRepo.save(p);
    }

    public StudentProfileResponseDto getFullProfile(Long studentId) {

        StudentProfile p = profileRepo.findById(studentId).orElseThrow();

        StudentAcademics ac = academicsRepo
                .findAll().stream()
                .filter(a -> a.getStudent().getId().equals(studentId))
                .findFirst().orElse(null);

        List<StudentSkill> skills = skillRepo.findAll().stream()
                .filter(s -> s.getStudent().getId().equals(studentId))
                .toList();

        List<StudentExperience> exp = expRepo.findAll().stream()
                .filter(e -> e.getStudent().getId().equals(studentId))
                .toList();

        List<StudentDocument> docs = docRepo.findAll().stream()
                .filter(d -> d.getStudent().getId().equals(studentId))
                .toList();

        return new StudentProfileResponseDto(p, ac, skills, exp, docs);
    }

}

