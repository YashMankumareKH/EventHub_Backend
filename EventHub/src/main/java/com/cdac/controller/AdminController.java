package com.cdac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private UserService userService;

    @Autowired
    private EventService eventService;

    // List All Users
    @GetMapping("/users")
    public ResponseEntity<List<User>> listAllUser() {
        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }

    // Add a User
    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody UserRegistrationRequest req) {
        Object result = userService.registerUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // Get single user details
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserAllDetails(@PathVariable Long userId) {
        User user = userService.getUserAllDetails(userId);
        return ResponseEntity.ok(user);
    }

    // Update User Details
    @PutMapping("/users/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody User user) {
        user.setId(userId);
        String result = userService.updateUser(user);
        return ResponseEntity.ok(result);
    }

    // Deactivate (soft-delete) a User
    @PatchMapping("/users/{userId}/deactivate")
    public ResponseEntity<String> deactivateUser(@PathVariable Long userId) {
        String result = userService.deleteUser(userId); 
        return ResponseEntity.ok(result);
    }

    // Get All Global Events including non active, cancelled, deleted, completed
    @GetMapping("/events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getEvents();
        return ResponseEntity.ok(events);
    }

    // Deactivate (soft-delete) Event
    @PatchMapping("/events/{eventId}/deactivate")
    public ResponseEntity<String> deactivateEvent(@PathVariable Long eventId) {
        String result = eventService.deleteEvent(eventId);
        return ResponseEntity.ok(result);
    }

    // List All managers
    @GetMapping("/managers")
    public ResponseEntity<List<User>> getAllManagers() {
        List<User> managers = userService.getAllManagers();
        return ResponseEntity.ok(managers);
    }

    // Get All Events of a Manager
    @GetMapping("/managers/{managerId}/events")
    public ResponseEntity<List<Event>> getAllEventsOfManager(@PathVariable Long managerId) {
        List<Event> events = eventService.getAllEventsOfManager(managerId);
        return ResponseEntity.ok(events);
    }
}


