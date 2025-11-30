package com.cdac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.AuthRequest;
import com.cdac.dto.UserProfileDTO;
import com.cdac.repo.UserRepo;
import com.cdac.dto.UserRegistrationRequest;
import com.cdac.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")

public class UserController {

	
	

	@Autowired
	private UserService userService;

    
	
	@PostMapping("/signin")
	public ResponseEntity<?> userAuthentication(@RequestBody @Valid
			AuthRequest dto) {
		System.out.println("in sign in "+dto);
		
		//invoke service layer method
			return ResponseEntity.ok(userService.authenticate(dto));
	}
	
	@GetMapping("/{userId}")
	public UserProfileDTO getUserDetails(@PathVariable Long userId) {
		return userService.getUserDetails(userId);
	}
	
	@PatchMapping("/{userId}")
	public String updateUserDetails(@PathVariable Long userId,@RequestBody UserProfileDTO userProfileDTO) {
		
		return userService.updateUserDetails(userId,userProfileDTO);
	}
	
	
	
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(
	        @RequestBody @Valid UserRegistrationRequest req) {

	    return ResponseEntity.ok(userService.registerUser(req));
	}

}

