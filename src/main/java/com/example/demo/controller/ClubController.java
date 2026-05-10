package com.example.demo.controller;

import com.example.demo.dto.ParticipantDto;
import com.example.demo.service.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("api/v1/club")
@RestController
@AllArgsConstructor
public class ClubController {
    private final ClubService clubService;

    @GetMapping("/check/{qrUuid}")
    public ParticipantDto processQr(@PathVariable UUID qrUuid){
        return clubService.processQR(qrUuid);
    }
}
