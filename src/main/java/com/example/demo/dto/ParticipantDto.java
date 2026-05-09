package com.example.demo.dto;

public class ParticipantDto {

    private final String firstName;
    private final String lastName;
    private final String middleName;

    public ParticipantDto(String firstName, String lastName, String middleName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
