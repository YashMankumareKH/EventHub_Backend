package com.cdac.service;

import com.cdac.dto.AuthRequest;
import com.cdac.dto.AuthResponse;
import com.cdac.dto.UserProfileDTO;
import com.cdac.entities.User;

public interface UserService {

	public User getUserByEmailId(String emailId);
	AuthResponse authenticate(AuthRequest dto);
	public UserProfileDTO getUserDetails(Long userId);
	public String updateUserDetails(Long userId, UserProfileDTO userProfileDTO);
}
