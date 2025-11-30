package com.cdac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.UserRegistrationRequest;
import com.cdac.entities.Event;
import com.cdac.entities.User;
import com.cdac.service.EventService;
import com.cdac.service.UserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {
	
	@Autowired
	UserService userService;
	@Autowired
	EventService eventService;
	
	//List All Users 
	@GetMapping("/users")
	public List<User> listAllUser(){
		
		return userService.getAllUser();
	}
	
	
	//Add a User
	@PostMapping("/users")
	public ResponseEntity<?> addUser(@RequestBody UserRegistrationRequest req) {
		
		return ResponseEntity.ok(userService.registerUser(req));
	}
	
	//Delete an User
	@PatchMapping("/users/{userId}/delete")
	public String deleteUser(@PathVariable Long userId) {
		
		
		return userService.deleteUser(userId);
		
	}
	
	@PutMapping("users/{userId}/update")
	public String updateUser(@RequestBody User user) {
		
		return userService.updateUser(user);
	}
	
	@GetMapping("user/{userId}")
	public User getUserAllDetails(@PathVariable Long userId) {
		
		return userService.getUserAllDetails(userId);
	}
	
	@GetMapping("admin/events")
	public List<Event> getAllEvents(){
		
		return eventService.getEvents();
	}

	
	@PatchMapping("/events/{eventId}/isActive")
	public String deleteevent(@PathVariable Long eventId ) {
		
		return eventService.deleteEvent(eventId);
		
	}
	
	@GetMapping("/managers")
	public List<User> getAllManagers(){
		
		return userService.getAllManagers();
	}
	
	@GetMapping("/managers/{managerId}/events")
	public List<Event> getAllEventsOfManager(@PathVariable Long managerId){
		
		return eventService.getAllEventsOfManager(managerId);
	}
}
