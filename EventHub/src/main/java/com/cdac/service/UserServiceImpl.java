package com.cdac.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.custom_exception.AuthenticationException;
import com.cdac.dto.AuthRequest;
import com.cdac.dto.AuthResponse;
import com.cdac.entities.User;
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
	

	@Override
	public User getUserByEmailId(String emailId) {
		
		
		return userRepo.findUserByEmailId(emailId).orElse(null);
	}


	@Override
	public AuthResponse authenticate(AuthRequest dto) {
		User user=userRepo.findByEmailAndPassword(dto.getEmail(), dto.getPassword()) //Optional<User>
				.orElseThrow(() -> new AuthenticationException("Invalid Email or password !!!!!!!"));
		
		AuthResponse respDTO = modelMapper.map(user, AuthResponse.class);
		 respDTO.setMessage("Login Successful!");
		return respDTO;
	}

}
