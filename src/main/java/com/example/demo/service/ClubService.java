package com.example.demo.service;

import com.example.demo.entity.Participant;
import com.example.demo.entity.QrCode;
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

    public ClubService(ParticipantRepo participantRepo, QrCodeRepo qrCodeRepo) {
        this.participantRepo = participantRepo;
        this.qrCodeRepo = qrCodeRepo;
    }

    public String processQR(UUID qrUuid) {
        Optional<QrCode> qrCode = qrCodeRepo.findByQrUuid(qrUuid);
        if (qrCode.isPresent()) {
            QrCode code = qrCode.get();
            Participant participant = code.getParticipant();
            code.setQrUuid(UUID.randomUUID());
            qrCodeRepo.save(code);
            return participant.getFirstName() + " " + participant.getLastName() + " " + participant.getMiddleName();

        } else {
            return "QR не найден";
        }
    }
}
