package com.cdac.dto;


import com.cdac.entities.UserRole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

	@Getter
	@Setter
	@NoArgsConstructor
	public class AuthResponse {
		private Long id;
		private String firstName;
		private String lastName;
		private UserRole role;
		private String message;
}
