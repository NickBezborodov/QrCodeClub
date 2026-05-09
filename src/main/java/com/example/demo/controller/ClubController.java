package com.example.demo.controller;

import com.example.demo.dto.ParticipantDto;
import com.example.demo.service.ClubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ClubController {
    private final ClubService clubService;

    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/process/{qrUuid}")
    public ParticipantDto processQr(@PathVariable UUID qrUuid){
        return clubService.processQR(qrUuid);
    }
}
