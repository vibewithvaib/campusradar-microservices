package com.campus.driveservice.service;

import com.campus.driveservice.dto.CreateDriveRequestDto;
import com.campus.driveservice.dto.DriveResponseDto;
import com.campus.driveservice.model.Drive;
import com.campus.driveservice.model.DriveCriteria;
import com.campus.driveservice.model.DriveRound;
import com.campus.driveservice.model.DriveStatus;
import com.campus.driveservice.repository.DriveCriteriaRepository;
import com.campus.driveservice.repository.DriveRepository;
import com.campus.driveservice.repository.DriveRoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DriveService {

    private final DriveRepository driveRepo;
    private final DriveCriteriaRepository criteriaRepo;
    private final DriveRoundRepository roundRepo;

    public DriveResponseDto createDrive(
            String recruiterEmail,
            CreateDriveRequestDto dto
    ) {

        Drive drive = new Drive();
        drive.setCompanyName(dto.getCompanyName());
        drive.setRole(dto.getRole());
        drive.setDescription(dto.getDescription());
        drive.setRecruiterEmail(recruiterEmail);
        driveRepo.save(drive);

        DriveCriteria criteria = new DriveCriteria();
        criteria.setDrive(drive);
        criteria.setMinTenth(dto.getMinTenth());
        criteria.setMinTwelfth(dto.getMinTwelfth());
        criteria.setRequiredSkills(String.join(",", dto.getSkills()));
        criteriaRepo.save(criteria);

        List<DriveRound> rounds = dto.getRounds().stream().map(r -> {
            DriveRound dr = new DriveRound();
            dr.setDrive(drive);
            dr.setName(r.getName());
            dr.setRoundNumber(r.getRoundNumber());
            return dr;
        }).toList();
        roundRepo.saveAll(rounds);

        return buildResponse(drive);
    }

    public DriveResponseDto startDrive(Long id) {
        Drive drive = driveRepo.findById(id).orElseThrow();
        drive.setStatus(DriveStatus.ACTIVE);
        driveRepo.save(drive);
        return buildResponse(drive);
    }

    @Transactional(readOnly = true)
    public DriveResponseDto getDrive(Long id) {
        Drive drive = driveRepo.findById(id).orElseThrow();
        return buildResponse(drive);
    }

    @Transactional(readOnly = true)
    public List<DriveResponseDto> getDrivesForRole(
            String role, String email
    ) {

        if ("RECRUITER".equals(role)) {
            return driveRepo.findByRecruiterEmail(email)
                    .stream().map(this::buildResponse).toList();
        }

        if ("TPO".equals(role) || "ADMIN".equals(role)) {
            return driveRepo.findAll()
                    .stream().map(this::buildResponse).toList();
        }

        if ("STUDENT".equals(role)) {
            return driveRepo.findByStatus(DriveStatus.ACTIVE)
                    .stream().map(this::buildResponse).toList();
        }

        return List.of();
    }

    private DriveResponseDto buildResponse(Drive d) {
        return new DriveResponseDto(
                d.getId(),
                d.getCompanyName(),
                d.getRole(),
                d.getDescription(),
                d.getRecruiterEmail(),
                d.getStatus(),
                criteriaRepo.findByDrive(d),
                roundRepo.findByDrive(d)
        );
    }
}
