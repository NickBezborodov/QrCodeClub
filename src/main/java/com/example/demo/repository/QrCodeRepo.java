package com.example.demo.repository;

import com.example.demo.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrCodeRepo extends JpaRepository<QrCode, Long> {
    Optional<QrCode> findByQrUuid(UUID qrUuid);
}
