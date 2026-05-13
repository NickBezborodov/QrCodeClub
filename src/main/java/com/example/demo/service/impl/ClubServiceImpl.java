package com.example.demo.service.impl;

import com.example.demo.dto.ParticipantDto;
import com.example.demo.entity.Participant;
import com.example.demo.entity.QrCode;
import com.example.demo.exception.QrNotFoundException;
import com.example.demo.mapper.ParticipantMapper;
import com.example.demo.repository.ParticipantRepo;
import com.example.demo.repository.QrCodeRepo;
import com.example.demo.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClubServiceImpl implements ClubService {
    private final ParticipantRepo participantRepo;
    private final QrCodeRepo qrCodeRepo;
    private final ParticipantMapper participantMapper;

    @Transactional
    public ParticipantDto processQR(UUID qrUuid) {
        Optional<QrCode> qrCode = qrCodeRepo.findByQrUuid(qrUuid);
        QrCode code = qrCode.orElseThrow(() -> new QrNotFoundException("QR-код не найден"));
        Participant participant = code.getParticipant();
        code.setQrUuid(UUID.randomUUID());
        qrCodeRepo.save(code);
        return participantMapper.map(participant);

    }

    public List<ParticipantDto> getAllParticipants() {
        return participantRepo.findAll().stream()
                .map(participantMapper::map)
                .toList();
    }

    @Transactional
    public ParticipantDto addParticipant(ParticipantDto dto) {
        Participant participant = Participant.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .build();

        participant = participantRepo.save(participant);

        QrCode qrCode = QrCode.builder()
                .qrUuid(UUID.randomUUID())
                .participant(participant)
                .build();

        qrCodeRepo.save(qrCode);

        return participantMapper.map(participant);
    }

    @Transactional
    public ParticipantDto updateParticipant(Long id, ParticipantDto dto) {
        Participant participant = participantRepo.findById(id)
                .orElseThrow();
        participant.setFirstName(dto.getFirstName());
        participant.setLastName(dto.getLastName());
        participant.setMiddleName(dto.getMiddleName());

        participant = participantRepo.save(participant);
        return participantMapper.map(participant);
    }

    @Transactional
    public void deleteParticipant(Long id) {
        Participant participant = participantRepo.findById(id).orElseThrow();
        participantRepo.delete(participant);
    }
}
