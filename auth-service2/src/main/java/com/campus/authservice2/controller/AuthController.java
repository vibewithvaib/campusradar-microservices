package com.campus.authservice2.controller;

import com.campus.authservice2.entity.Role;
import com.campus.authservice2.service.AuthService;
import com.campus.authservice2.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final JwtUtil jwtUtil;

    @PostMapping("/create-tpo")
    public ResponseEntity<?> createTpo(
            @RequestHeader("Authorization") String auth,
            @RequestParam String email,
            @RequestParam String password
    ) {
        checkRole(auth, "ADMIN");
        service.createUser(email, Role.TPO, password);
        return ResponseEntity.ok("TPO CREATED");
    }

    @PostMapping("/create-student")
    public ResponseEntity<?> createStudent(
            @RequestHeader("Authorization") String auth,
            @RequestParam String email,
            @RequestParam String password
    ) {
        checkRole(auth, "ADMIN");
        service.createUser(email, Role.STUDENT, password);
        return ResponseEntity.ok("STUDENT CREATED");
    }

    @PostMapping("/create-recruiter")
    public ResponseEntity<?> createRecruiter(
            @RequestHeader("Authorization") String auth,
            @RequestParam String email,
            @RequestParam String password
    ) {
        checkRole(auth, "TPO");
        service.createUser(email, Role.RECRUITER, password);
        return ResponseEntity.ok("RECRUITER CREATED");
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password
    ) {
        return service.login(email, password);
    }

    // helper
    private void checkRole(String authHeader, String requiredRole) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing token");
        }

        String token = authHeader.substring(7);
        Claims claims = jwtUtil.parse(token);

        if (!requiredRole.equals(claims.get("role"))) {
            throw new RuntimeException("Forbidden");
        }
    }
}

