package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantDto {

    private final String firstName;
    private final String lastName;
    private final String middleName;

}
