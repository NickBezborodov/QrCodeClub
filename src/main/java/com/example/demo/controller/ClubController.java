package com.example.demo.controller;

import com.example.demo.dto.ParticipantDto;
import com.example.demo.service.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/club")
@RestController
@AllArgsConstructor
public class ClubController {
    private final ClubService clubService;

    @GetMapping("/check/{qrUuid}")
    public ParticipantDto processQr(@PathVariable UUID qrUuid) {
        return clubService.processQR(qrUuid);
    }

    @GetMapping
    public List<ParticipantDto> getAllParticipants() {
        return clubService.getAllParticipants();
    }

    @PostMapping("/add/participant")
    public ParticipantDto addParticipant(@RequestBody ParticipantDto dto) {
        return clubService.addParticipant(dto);
    }

    @PutMapping("/{id}")
    public ParticipantDto updateParticipant(@PathVariable Long id, @RequestBody ParticipantDto dto) {
        return clubService.updateParticipant(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteParticipant(@PathVariable Long id) {
        clubService.deleteParticipant(id);
    }

}
