package com.example.demo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantDto {

    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 1, message ="Не должно быть меньше 1 символа")
    private final String firstName;
    @NotBlank(message = "Фамилия не должна быть пустой")
    @Size(min = 1, message ="Не должно быть меньше 1 символа")
    private final String lastName;
    @Size(min = 1, message ="Не должно быть меньше 1 символа")
    private final String middleName;

}
