package com.example.demo.exception;

public class QrNotFoundException extends RuntimeException {

    public QrNotFoundException(String message) {
        super(message);
    }
}

