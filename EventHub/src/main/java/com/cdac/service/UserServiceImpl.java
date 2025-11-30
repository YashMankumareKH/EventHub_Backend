package com.cdac.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cdac.custom_exception.AuthenticationException;
import com.cdac.dto.AuthRequest;
import com.cdac.dto.AuthResponse;
import com.cdac.dto.EventList;
import com.cdac.dto.UserProfileDTO;
import com.cdac.dto.UserRegistrationRequest;
import com.cdac.dto.UserRegistrationResponse;
import com.cdac.entities.User;
import com.cdac.entities.UserRole;
import com.cdac.repo.UserRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	private final ModelMapper modelMapper;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	

	@Override
	public User getUserByEmailId(String emailId) {
		
		
		return userRepo.findUserByEmailId(emailId).orElse(null);
	}


	@Override
	public AuthResponse authenticate(AuthRequest dto) {

	    User user = userRepo.findByEmailId(dto.getEmail())
	            .orElseThrow(() -> new AuthenticationException("Invalid Email or password !!!!!!!!"));

	    // Validate encrypted password
	    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
	        throw new AuthenticationException("Invalid Email or password !!!!!!!!");
	    }

	    // Prepare response
	    AuthResponse resp = modelMapper.map(user, AuthResponse.class);
	    resp.setMessage("Login Successful!");

	    return resp;
	}



	@Override
	public UserRegistrationResponse registerUser(UserRegistrationRequest req) {
		
		 if (userRepo.existsByEmailId(req.getEmailId())) {
	            throw new RuntimeException("Email already registered");
	        }
		 if (userRepo.existsByPhone(req.getPhone())) {
		        throw new RuntimeException("Phone number already registered");
		    }

	        User user = new User();
	        user.setFirstName(req.getFirstName());
	        user.setLastName(req.getLastName());
	        user.setEmailId(req.getEmailId());
	        user.setPhone(req.getPhone());
	        user.setAddress(req.getAddress());
	        user.setRole(UserRole.valueOf(req.getRole().toUpperCase()));

	        user.setPassword(passwordEncoder.encode(req.getPassword()));

	        User saved = userRepo.save(user);

	        UserRegistrationResponse resp = new UserRegistrationResponse();
	        resp.setUserId(saved.getId());
	        resp.setFirstName(saved.getFirstName());
	        resp.setLastName(saved.getLastName());
	        resp.setEmailId(saved.getEmailId());
	        resp.setPhone(saved.getPhone());
	        resp.setRole(saved.getRole().name());
	        resp.setMessage("User registered successfully");

	        return resp;
	}


	@Override
	public UserProfileDTO getUserDetails(Long userId) {
		User user = userRepo.findById(userId).orElseThrow();
		UserProfileDTO userProfileDTO = modelMapper.map(user, UserProfileDTO.class);
		return userProfileDTO;
	}


	@Override
	public String updateUserDetails(Long userId,UserProfileDTO userProfileDTO) {
		User user = userRepo.findById(userId).orElseThrow();
		
		user.setFirstName(userProfileDTO.getFirstName());
		user.setLastName(userProfileDTO.getLastName());
		user.setEmailId(userProfileDTO.getEmailId());
		user.setPassword(passwordEncoder.encode(userProfileDTO.getPassword()));
		user.setPhone(userProfileDTO.getPhone());
		user.setAddress(userProfileDTO.getAddress());
		
		return "Profile Updated";
	}


	@Override
	public List<EventList> getUpcomingEvents(Long userId) {
		User user = userRepo.findById(userId).orElseThrow();
		
		List<>
		
		
		
		return null;
	}

}
