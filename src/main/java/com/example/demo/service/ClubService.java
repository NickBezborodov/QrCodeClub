package com.example.demo.service;

import com.example.demo.dto.ParticipantDto;
import com.example.demo.entity.Participant;
import com.example.demo.entity.QrCode;
import com.example.demo.mapper.ParticipantMapper;
import com.example.demo.repository.ParticipantRepo;
import com.example.demo.repository.QrCodeRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.Optional;


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
}
