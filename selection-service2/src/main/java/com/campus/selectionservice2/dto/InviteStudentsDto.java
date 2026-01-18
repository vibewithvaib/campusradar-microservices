package com.campus.selectionservice2.dto;

import lombok.Data;

import java.util.List;

@Data
public class InviteStudentsDto {
    private Long driveId;
    private List<String> studentEmails;
}

