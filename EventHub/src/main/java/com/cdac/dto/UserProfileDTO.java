package com.cdac.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDTO {
	
	
	private String firstName;
	private String lastName;
	private String emailId;
	private String password;
	private Long phone;
	private String address;

}
