package com.example.demo.service;

import com.example.demo.dto.ParticipantDto;
import com.example.demo.entity.Participant;
import com.example.demo.entity.QrCode;
import com.example.demo.mapper.ParticipantMapper;
import com.example.demo.repository.ParticipantRepo;
import com.example.demo.repository.QrCodeRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ClubService {

    private final ParticipantRepo participantRepo;
    private final QrCodeRepo qrCodeRepo;
    private final ParticipantMapper participantMapper;

    public ClubService(ParticipantRepo participantRepo, QrCodeRepo qrCodeRepo, ParticipantMapper participantMapper) {
        this.participantRepo = participantRepo;
        this.qrCodeRepo = qrCodeRepo;
        this.participantMapper = participantMapper;
    }

    public ParticipantDto processQR(UUID qrUuid) {
        Optional<QrCode> qrCode = qrCodeRepo.findByQrUuid(qrUuid);
        QrCode code = qrCode.orElseThrow();
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

    public ParticipantDto updateParticipant(Long id, ParticipantDto dto) {
        Participant participant = participantRepo.findById(id)
                .orElseThrow();
        participant.setFirstName(dto.getFirstName());
        participant.setLastName(dto.getLastName());
        participant.setMiddleName(dto.getMiddleName());

        participant = participantRepo.save(participant);
        return participantMapper.map(participant);
    }

    public void deleteParticipant(Long id) {
        Participant participant = participantRepo.findById(id).orElseThrow();
        participantRepo.delete(participant);
    }
}

