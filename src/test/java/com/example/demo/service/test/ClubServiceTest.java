package com.example.demo.service.test;


import com.example.demo.dto.ParticipantDto;
import com.example.demo.entity.Participant;
import com.example.demo.entity.QrCode;
import com.example.demo.exception.QrNotFoundException;
import com.example.demo.mapper.ParticipantMapper;
import com.example.demo.repository.ParticipantRepo;
import com.example.demo.repository.QrCodeRepo;
import com.example.demo.service.impl.ClubServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ClubServiceTest {
    @Mock
    private ParticipantRepo participantRepo;
    @Mock
    private QrCodeRepo qrCodeRepo;
    @Mock
    private ParticipantMapper participantMapper;

    @InjectMocks
    private ClubServiceImpl clubService;

    @Test
    void whatDoesTheFullName() {
        // 1. Create a fixed UUID
        UUID testUuid = UUID.fromString("22174d5c-1d64-46fe-aebc-bd3601c4a72f");
        Participant participant = Participant.builder()
                .firstName("Иван")
                .lastName("Иванович")
                .build();

        QrCode qrCode = QrCode.builder()
                .qrUuid(testUuid)
                .participant(participant)
                .build();

        // 2. Mock the static UUID.randomUUID() method
        when(qrCodeRepo.findByQrUuid(testUuid)).thenReturn(Optional.of(qrCode));

        ParticipantDto participantDto = new ParticipantDto("Иван", "Иванович", null);
        when(participantMapper.map(participant)).thenReturn(participantDto);

        ParticipantDto result = clubService.processQR(testUuid);

        assertEquals(participantDto, result);
    }

    @Test
    void qrNotFound() {
        UUID testUuid = UUID.fromString("22174d5c-1d64-46fe-aebc-bd3601c4a72f");

        when(qrCodeRepo.findByQrUuid(testUuid)).thenReturn(Optional.empty());
        // Act & Assert
        assertThrows(
                QrNotFoundException.class,
                () -> clubService.processQR(testUuid)
        );
    }

    @Test
    void uuidChangesAfterLoggingIn() {
        UUID testUuid = UUID.fromString("22174d5c-1d64-46fe-aebc-bd3601c4a72f");
        Participant participant = Participant.builder()
                .firstName("Иван")
                .lastName("Иванович")
                .build();

        QrCode qrCode = QrCode.builder()
                .qrUuid(testUuid)
                .participant(participant)
                .build();

        when(qrCodeRepo.findByQrUuid(testUuid)).thenReturn(Optional.of(qrCode));

        ParticipantDto participantDto = new ParticipantDto("Иван", "Иванович", null);
        when(participantMapper.map(participant)).thenReturn(participantDto);

        ParticipantDto result = clubService.processQR(testUuid);

        assertNotEquals(testUuid, qrCode.getQrUuid());
    }
}

/**
 * ТЕСТЫ CLUB SERVICE — ЧТО ПРОВЕРЯЕМ:
 *
 * 1. whatDoesTheFullName() — processQR возвращает правильное ФИО по UUID
 *    Мокаем: qrCodeRepo.findByQrUuid → QrCode с участником
 *            participantMapper.map → DTO с ожидаемым ФИО
 *    Проверяем: result совпадает с expectedDto
 *
 * 2. qrNotFound() — processQR выбрасывает QrNotFoundException, если QR не найден
 *    Мокаем: qrCodeRepo.findByQrUuid → Optional.empty()
 *    Проверяем: assertThrows ловит исключение
 *
 * 3. uuidChangesAfterLoggingIn() — после входа UUID меняется на новый
 *    Мокаем: то же, что в тесте 1
 *    Проверяем: qrCode.getQrUuid() != testUuid (UUID изменился)
 *
 * ПРИНЦИПЫ:
 * - Мокаем всё, что вне тестируемого метода (репозитории, мапперы)
 * - Не мокаем то, что тестируем (ClubServiceImpl)
 * - Один тест = одна проверка
 */