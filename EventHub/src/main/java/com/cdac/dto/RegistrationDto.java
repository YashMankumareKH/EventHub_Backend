package com.cdac.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.cdac.entities.RegStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegistrationDto {

    private Long id;
    private Long userId;
    private Long eventId;

    private LocalDate registrationDate;
    private LocalTime registrationTime;

    private RegStatus status;   
}
