package com.example.demo.service;

import com.example.demo.dto.ParticipantDto;

import java.util.List;
import java.util.UUID;

public interface ClubService {
    ParticipantDto processQR(UUID qrUuid);

    List<ParticipantDto> getAllParticipants();

    ParticipantDto addParticipant(ParticipantDto dto);

    ParticipantDto updateParticipant(Long id, ParticipantDto dto);

    void deleteParticipant(Long id);
}