package com.example.demo.repository;

import com.example.demo.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodeRepo extends JpaRepository<QrCode, Long> {
}
