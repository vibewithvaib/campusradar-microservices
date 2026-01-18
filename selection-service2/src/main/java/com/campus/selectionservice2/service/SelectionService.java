package com.campus.selectionservice2.service;

import com.campus.selectionservice2.dto.InviteStudentsDto;
import com.campus.selectionservice2.dto.ShortlistDto;
import com.campus.selectionservice2.model.*;
import com.campus.selectionservice2.repository.FinalPlacementRepository;
import com.campus.selectionservice2.repository.RoundSelectionRepository;
import com.campus.selectionservice2.repository.StudentDriveRepository;
import com.campus.selectionservice2.repository.StudentTimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SelectionService {

    private final StudentDriveRepository studentDriveRepo;
    private final RoundSelectionRepository roundRepo;
    private final FinalPlacementRepository finalRepo;
    private final StudentTimelineRepository timelineRepo;
    private final RestTemplate restTemplate;

    /* INVITE STUDENTS (from drive-service) */
    public void invite(InviteStudentsDto dto) {
        for (String email : dto.getStudentEmails()) {
            StudentDrive sd = new StudentDrive();
            sd.setDriveId(dto.getDriveId());
            sd.setStudentEmail(email);
            studentDriveRepo.save(sd);

            log(email, dto.getDriveId(), "INVITED TO DRIVE");
        }
    }

    /* STUDENT ACCEPTS DRIVE */
    public void acceptDrive(String email, Long driveId) {
        StudentDrive sd = studentDriveRepo
                .findAll().stream()
                .filter(s -> s.getStudentEmail().equals(email)
                        && s.getDriveId().equals(driveId))
                .findFirst().orElseThrow();

        sd.setStatus(SelectionStatus.ACCEPTED);
        studentDriveRepo.save(sd);
        log(email, driveId, "DRIVE ACCEPTED");
    }

    /* RECRUITER SHORTLISTS NEXT ROUND */
    public void shortlist(ShortlistDto dto) {
        StudentDrive sd = studentDriveRepo.findById(dto.getStudentDriveId())
                .orElseThrow();

        sd.setCurrentRound(dto.getNextRound());
        sd.setStatus(SelectionStatus.IN_PROGRESS);
        studentDriveRepo.save(sd);

        RoundSelection rs = new RoundSelection();
        rs.setStudentDriveId(sd.getId());
        rs.setRoundNumber(dto.getNextRound());
        rs.setStatus(SelectionStatus.IN_PROGRESS);
        roundRepo.save(rs);

        log(sd.getStudentEmail(), sd.getDriveId(), "SHORTLISTED TO ROUND " + dto.getNextRound());
    }

    /* FINAL SELECT */
    public void finalSelect(Long studentDriveId) {
        StudentDrive sd = studentDriveRepo.findById(studentDriveId).orElseThrow();
        sd.setStatus(SelectionStatus.FINAL_SELECTED);
        studentDriveRepo.save(sd);

        finalRepo.save(new FinalPlacement(
                null,
                sd.getStudentEmail(),
                sd.getDriveId(),
                false,
                LocalDateTime.now()
        ));

        log(sd.getStudentEmail(), sd.getDriveId(), "FINAL SELECTED");
    }

    /* STUDENT ACCEPTS OFFER */
    public void acceptOffer(String email, Long driveId,String token) {
        FinalPlacement fp = finalRepo.findAll().stream()
                .filter(f -> f.getStudentEmail().equals(email)
                        && f.getDriveId().equals(driveId))
                .findFirst().orElseThrow();

        fp.setAccepted(true);
        finalRepo.save(fp);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        // 🔐 lock student profile
        restTemplate.exchange(
                "http://localhost:8081/api/profile/blacklist/email/" + email,
                HttpMethod.PUT,
                entity,
                Void.class
        );

        log(email, driveId, "OFFER ACCEPTED");
    }

    private void log(String email, Long driveId, String action) {
        StudentTimeline t = new StudentTimeline();
        t.setStudentEmail(email);
        t.setDriveId(driveId);
        t.setAction(action);
        timelineRepo.save(t);
    }
}
