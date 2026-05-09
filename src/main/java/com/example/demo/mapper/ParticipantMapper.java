package com.example.demo.mapper;

import com.example.demo.dto.ParticipantDto;
import com.example.demo.entity.Participant;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMapper {
    public ParticipantDto map(Participant participant){
        return new ParticipantDto(
                participant.getFirstName(),
                participant.getLastName(),
                participant.getMiddleName()
        );
    }
}
