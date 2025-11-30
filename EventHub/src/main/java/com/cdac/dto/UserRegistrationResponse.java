package com.cdac.dto;

import lombok.Data;

@Data
public class UserRegistrationResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private Long phone;
    private String role;
    private String message;
}
