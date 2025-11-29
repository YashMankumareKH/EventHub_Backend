package com.cdac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cdac.dto.RegistrationDto;
import com.cdac.service.EventRegistrationService;

@RestController
@RequestMapping("/event-registration")
@CrossOrigin(origins = "http://localhost:3000")
public class EventRegistrationController {

    @Autowired
    private EventRegistrationService registrationService;

    public EventRegistrationController() {
        System.out.println("In Constructor " + getClass());
    }

    // 1. Register for an event
    @PostMapping
    public RegistrationDto registerEvent(@RequestBody RegistrationDto dto) {
        System.out.println("RegisteringEvent: " + dto);
        return registrationService.registerForEvent(dto);
    }

    // 2. here we Get registrations of a user
    @GetMapping("/user/{userId}")
    public List<RegistrationDto> getUserRegistrations(@PathVariable Long userId) {
        System.out.println("Get user registrations: " + userId);
        return registrationService.getUserEventRegistrations(userId);
    }

    // 3. we Get all registrations of a single event
    @GetMapping("/event/{eventId}")
    public List<RegistrationDto> getEventRegistrations(@PathVariable Long eventId) {
        System.out.println("Get event registrations: " + eventId);
        return registrationService.getEventRegistrations(eventId);
    }

    // 4. Cancel registration
    @PostMapping("/cancel/{regId}")
    public String cancelRegistration(@PathVariable Long regId) {
        System.out.println("Cancel registration: " + regId);
        return registrationService.cancelEventRegistration(regId);
    }
}
