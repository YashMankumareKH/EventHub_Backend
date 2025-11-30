package com.cdac.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cdac.dto.AuthRequest;
import com.cdac.dto.AuthResponse;
import com.cdac.dto.EventList;
import com.cdac.dto.UserProfileDTO;
import com.cdac.dto.UserRegistrationRequest;
import com.cdac.dto.UserRegistrationResponse;
import com.cdac.entities.User;

@Service
public interface UserService {
	

	public User getUserByEmailId(String emailId);


	AuthResponse authenticate(AuthRequest dto);
	public UserProfileDTO getUserDetails(Long userId);
	public String updateUserDetails(Long userId, UserProfileDTO userProfileDTO);
	UserRegistrationResponse registerUser(UserRegistrationRequest req);


	public List<EventList> getUpcomingEvents(Long userId);


	public List<EventList> getCompletedEvents(Long userId);


	public List<User> getAllUser();


	public String deleteUser(Long userId);


	public String updateUser(User user);


	public User getUserAllDetails(Long userId);


	public List<User> getAllManagers();
}
