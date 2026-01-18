package com.campus.authservice.controller;

import com.campus.authservice.dto.AuthResponseDto;
import com.campus.authservice.dto.LoginRequestDto;
import com.campus.authservice.dto.SignupRequestDto;
import com.campus.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signup(
            @RequestBody SignupRequestDto dto) {

        return ResponseEntity.ok(
                new AuthResponseDto(authService.signup(dto))
        );
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @RequestBody LoginRequestDto dto) {

        return ResponseEntity.ok(
                new AuthResponseDto(authService.login(dto.getEmail(), dto.getPassword()))
        );
    }
}
