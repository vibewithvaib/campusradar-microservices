package com.campus.profileservice2.controller;

import com.campus.profileservice2.dto.RecruiterProfileRequestDto;
import com.campus.profileservice2.dto.StudentProfileRequestDto;
import com.campus.profileservice2.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;

    @PostMapping("/student")
    public ResponseEntity<?> createStudentProfile(
            @RequestBody StudentProfileRequestDto dto,
            HttpServletRequest request
    ) {
        if (!"STUDENT".equals(request.getAttribute("role"))) {
            return ResponseEntity.status(403).build();
        }
        service.saveStudentProfile((String) request.getAttribute("email"), dto);
        return ResponseEntity.ok("PROFILE SAVED");
    }

    @PutMapping("/verify/{id}")
    public ResponseEntity<?> verify(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        if (!"TPO".equals(request.getAttribute("role"))) {
            return ResponseEntity.status(403).build();
        }
        service.verifyStudent(id);
        return ResponseEntity.ok("VERIFIED");
    }

    @PostMapping("/recruiter")
    public ResponseEntity<?> createRecruiter(
            @RequestBody RecruiterProfileRequestDto dto,
            HttpServletRequest request
    ) {
        if (!"TPO".equals(request.getAttribute("role"))) {
            return ResponseEntity.status(403).build();
        }

        service.createRecruiterProfile(dto);
        return ResponseEntity.ok("RECRUITER PROFILE CREATED");
    }
    @GetMapping("/student/{id}")
    public ResponseEntity<?> getStudent(
            @PathVariable Long id,
            HttpServletRequest request
    ) {
        if (!"TPO".equals(request.getAttribute("role"))) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(service.getFullProfile(id));
    }


}

