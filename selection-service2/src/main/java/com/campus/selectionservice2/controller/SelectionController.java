package com.campus.selectionservice2.controller;

import com.campus.selectionservice2.dto.InviteStudentsDto;
import com.campus.selectionservice2.dto.ShortlistDto;
import com.campus.selectionservice2.service.SelectionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/selection")
@RequiredArgsConstructor
public class SelectionController {

    private final SelectionService service;

    @PostMapping("/invite")
    public ResponseEntity<?> invite(@RequestBody InviteStudentsDto dto) {
        service.invite(dto);
        return ResponseEntity.ok("INVITED");
    }

    @PostMapping("/accept/{driveId}")
    public ResponseEntity<?> accept(
            @PathVariable Long driveId,
            HttpServletRequest request
    ) {
        service.acceptDrive(
                (String) request.getAttribute("email"),
                driveId
        );
        return ResponseEntity.ok("ACCEPTED");
    }

    @PostMapping("/shortlist")
    public ResponseEntity<?> shortlist(
            @RequestBody ShortlistDto dto,
            HttpServletRequest request
    ) {
        if (!"RECRUITER".equals(request.getAttribute("role"))) {
            return ResponseEntity.status(403).build();
        }

        service.shortlist(dto);
        return ResponseEntity.ok("SHORTLISTED");
    }

    @PostMapping("/final-select/{id}")
    public ResponseEntity<?> finalSelect(@PathVariable Long id) {
        service.finalSelect(id);
        return ResponseEntity.ok("FINAL SELECTED");
    }

    @PostMapping("/offer-accept/{driveId}")
    public ResponseEntity<?> offerAccept(
            @PathVariable Long driveId,
            HttpServletRequest request
    ) {
        service.acceptOffer(
                (String) request.getAttribute("email"),
                driveId,
                request.getHeader("Authorization")
        );
        return ResponseEntity.ok("PLACED");
    }
}

