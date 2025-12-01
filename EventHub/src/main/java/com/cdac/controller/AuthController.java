package com.cdac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.AuthRequest;
import com.cdac.dto.UserRegistrationRequest;
import com.cdac.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/signin")
	public ResponseEntity<?> userAuthentication(@RequestBody @Valid AuthRequest dto) {
		System.out.println("in sign in " + dto);

		return ResponseEntity.ok(userService.authenticate(dto));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationRequest req) {

		return ResponseEntity.ok(userService.registerUser(req));
	}

}
