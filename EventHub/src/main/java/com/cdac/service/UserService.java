package com.cdac.service;

import java.util.List;

import com.cdac.dto.AuthRequest;
import com.cdac.dto.AuthResponse;
import com.cdac.dto.EventList;
import com.cdac.dto.UserProfileDTO;
import com.cdac.dto.UserRegistrationRequest;
import com.cdac.dto.UserRegistrationResponse;
import com.cdac.entities.User;

public interface UserService {
	

	public User getUserByEmailId(String emailId);


	AuthResponse authenticate(AuthRequest dto);
	public UserProfileDTO getUserDetails(Long userId);
	public String updateUserDetails(Long userId, UserProfileDTO userProfileDTO);
	UserRegistrationResponse registerUser(UserRegistrationRequest req);


	public List<EventList> getUpcomingEvents(Long userId);
}
