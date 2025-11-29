package com.cdac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cdac.entities.User;
import com.cdac.repo.UserRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	

	@Override
	public User getUserByEmailId(String emailId) {
		
		
		return userRepo.findUserByEmailId(emailId).orElse(null);
	}

}
