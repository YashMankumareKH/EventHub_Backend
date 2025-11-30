package com.cdac.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegistrationRequest {

    @NotBlank
    private String firstName;

    private String lastName;

    @NotBlank
    @Email
    private String emailId;

    @NotBlank
    private String password;

    @NotNull
    private Long phone;

    @NotBlank
    private String address;

    private String role; // PARTICIPANT / ORGANIZER
}

