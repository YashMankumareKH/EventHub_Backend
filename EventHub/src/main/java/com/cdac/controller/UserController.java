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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.EventList;
import com.cdac.dto.Eventdto;
import com.cdac.dto.UserProfileDTO;
import com.cdac.service.EventService;
import com.cdac.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173")


public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;


    //List all published events (user) 
    @GetMapping("/listEvents")
    public ResponseEntity<List<EventList>> getAllEvents() {
        System.out.println("Get all");
        List<EventList> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    //Get event details
    @GetMapping("/event/{eventId}")
    public ResponseEntity<Eventdto> getEventById(@PathVariable Long eventId) {
        Eventdto dto = eventService.getEventById(eventId);
        return ResponseEntity.ok(dto);
    }

    //Get user profile (self or admin)
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDTO> getUserDetails(@PathVariable Long userId) {
        UserProfileDTO profile = userService.getUserDetails(userId);
        return ResponseEntity.ok(profile);
    }

    //Update User Details
    @PatchMapping("/{userId}")
    public ResponseEntity<String> updateUserDetails(@PathVariable Long userId, @RequestBody UserProfileDTO userProfileDTO) {
        String result = userService.updateUserDetails(userId, userProfileDTO);
        return ResponseEntity.ok(result);
    }

    //List upcoming events of user 
    @GetMapping("/{userId}/events/upcoming")
    public ResponseEntity<List<EventList>> getUpcomingEvents(@PathVariable Long userId) {
        List<EventList> upcoming = userService.getUpcomingEvents(userId);
        return ResponseEntity.ok(upcoming);
    }

    //List Completed events of user
    @GetMapping("/{userId}/events/completed")
    public ResponseEntity<List<EventList>> getCompletedEvents(@PathVariable Long userId) {
        List<EventList> completed = userService.getCompletedEvents(userId);
        return ResponseEntity.ok(completed);
    }

    //Register For an Event
    @PostMapping("/{userId}/events/{eventId}/register")
    public ResponseEntity<String> registerForEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        String result = eventService.registerForEvent(userId, eventId);
        // Created is appropriate for a successful registration creation
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //Cancel Registration of event
    @PatchMapping("/{userId}/events/{eventId}/register")
    public ResponseEntity<String> cancelRegistrationForEvent(@PathVariable Long userId, @PathVariable Long eventId) {
        String result = eventService.cancelRegistrationForEvent(userId, eventId);
        return ResponseEntity.ok(result);
    }

}

