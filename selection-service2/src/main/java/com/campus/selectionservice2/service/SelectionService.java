package com.campus.selectionservice2.service;

import com.campus.selectionservice2.client.ProfileClient;
import com.campus.selectionservice2.dto.DriveProgressDto;
import com.campus.selectionservice2.dto.SelectionStatusDto;
import com.campus.selectionservice2.dto.StudentEligibilityDto;
import com.campus.selectionservice2.model.DriveSelection;
import com.campus.selectionservice2.model.FinalPlacement;
import com.campus.selectionservice2.repository.DriveSelectionRepository;
import com.campus.selectionservice2.repository.FinalPlacementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SelectionService {

    private final DriveSelectionRepository selectionRepo;
    private final FinalPlacementRepository placementRepo;
    private final ProfileClient profileClient;

    /* ================================
       RECRUITER: INVITE STUDENTS
       ================================ */
    public void inviteStudents(Long driveId) {

        List<StudentEligibilityDto> eligibleStudents =
                profileClient.getEligibleStudents();

        for (StudentEligibilityDto s : eligibleStudents) {

            if (selectionRepo
                    .findByDriveIdAndStudentEmail(driveId, s.getEmail())
                    .isPresent()) continue;

            DriveSelection ds = new DriveSelection();
            ds.setDriveId(driveId);
            ds.setStudentEmail(s.getEmail());
            ds.setCurrentRound(1);
            ds.setActive(true);
            ds.setUpdatedAt(LocalDateTime.now());
            selectionRepo.save(ds);
        }
    }

    /* ================================
       STUDENT: VIEW STATUS
       ================================ */
    @Transactional(readOnly = true)
    public List<SelectionStatusDto> getStudentStatus(String email) {

        return selectionRepo.findByStudentEmail(email)
                .stream()
                .map(s -> new SelectionStatusDto(
                        s.getDriveId(),
                        s.getCurrentRound(),
                        s.isActive(),
                        s.isSelected(),
                        s.isRejected()
                ))
                .toList();
    }

    /* ================================
       RECRUITER: SHORTLIST NEXT ROUND
       ================================ */
    public void shortlistNextRound(
            Long driveId,
            int totalRounds,
            List<String> selectedEmails
    ) {

        List<DriveSelection> active =
                selectionRepo.findByDriveIdAndActiveTrue(driveId);

        for (DriveSelection ds : active) {

            if (selectedEmails.contains(ds.getStudentEmail())) {

                if (ds.getCurrentRound() >= totalRounds) {
                    throw new RuntimeException("Max rounds reached");
                }

                ds.setCurrentRound(ds.getCurrentRound() + 1);
                ds.setUpdatedAt(LocalDateTime.now());
            } else {
                ds.setActive(false);
                ds.setRejected(true);
            }

            selectionRepo.save(ds);
        }
    }

    /* ================================
       RECRUITER: FINAL SELECT
       ================================ */
    public void finalSelect(Long driveId, List<String> selectedEmails) {

        for (String email : selectedEmails) {

            if (placementRepo.existsByStudentEmail(email)) {
                throw new RuntimeException("Student already placed");
            }

            DriveSelection ds = selectionRepo
                    .findByDriveIdAndStudentEmail(driveId, email)
                    .orElseThrow();

            ds.setSelected(true);
            ds.setActive(false);
            ds.setUpdatedAt(LocalDateTime.now());
            selectionRepo.save(ds);

            FinalPlacement fp = new FinalPlacement();
            fp.setDriveId(driveId);
            fp.setStudentEmail(email);
            fp.setAccepted(false);
            fp.setPlacedAt(LocalDateTime.now());
            placementRepo.save(fp);
        }
    }

    /* ================================
       STUDENT: ACCEPT OFFER
       ================================ */
    public void acceptOffer(String email, Long driveId) {

        FinalPlacement fp = placementRepo.findByStudentEmailAndDriveId(email,driveId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        fp.setAccepted(true);
        placementRepo.save(fp);

        profileClient.blacklistStudent(email);
    }

    /* ================================
       DASHBOARD: DRIVE PROGRESS
       ================================ */
    @Transactional(readOnly = true)
    public DriveProgressDto getDriveProgress(Long driveId) {

        List<DriveSelection> all = selectionRepo.findByDriveId(driveId);

        int total = all.size();
        int active = (int) all.stream().filter(DriveSelection::isActive).count();
        int rejected = (int) all.stream().filter(DriveSelection::isRejected).count();
        int selected = (int) all.stream().filter(DriveSelection::isSelected).count();

        return new DriveProgressDto(driveId, total, active, rejected, selected);
    }

}