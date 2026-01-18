package com.campus.authservice.dto;

import com.campus.authservice.entities.Role;
import lombok.Data;

@Data
public class SignupRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}


