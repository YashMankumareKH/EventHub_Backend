package com.cdac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RegisteredUserDto {

	
	private Long userId;
	private String firstName;
	private String lastName;
	private String emailId;
	private Long phone;
	public RegisteredUserDto(Long userId, String firstName, String lastName, String emailId, Long phone) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.phone = phone;
	}
	

	
}
