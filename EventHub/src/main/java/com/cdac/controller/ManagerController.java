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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.Eventdto;
import com.cdac.dto.RegisteredUserDto;
import com.cdac.service.EventService;

@RestController
@RequestMapping("/manager")
@CrossOrigin(origins = "http://localhost:5173")


public class ManagerController {

    @Autowired
    private EventService eventService;

    // Create a new event (Manager only)
    @PostMapping("/{managerId}")
    public ResponseEntity<String> addEvent(@PathVariable Long managerId, @RequestBody Eventdto event) {
        String result = eventService.addEvent(managerId, event);
        // Created is appropriate for a successful creation
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // Update event (only owner manager)
    @PutMapping("/event/{eventId}")
    public ResponseEntity<String> updateEventDetails(@PathVariable Long eventId, @RequestBody Eventdto dto) {
        dto.setId(eventId);
        String result = eventService.updateEventDetails(dto);
        return ResponseEntity.ok(result);
    }

    // Get all Manager Events
    @GetMapping("/event/{managerId}")
    public ResponseEntity<List<Eventdto>> listManagerEvent(@PathVariable Long managerId) {
        List<Eventdto> events = eventService.getUserEvents(managerId);
        return ResponseEntity.ok(events);
    }

    // Delete Event (Manager)
    @PatchMapping("/event/{eventId}/delete")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        String result = eventService.deleteEvent(eventId);
        // If delete returns a message, OK is fine; alternatively you could use NO_CONTENT when no body is desired.
        return ResponseEntity.ok(result);
    }

    // Registrations of particular Event
    @GetMapping("/event/{eventId}/attendees")
    public ResponseEntity<List<RegisteredUserDto>> getEventRegistrations(@PathVariable Long eventId) {
        List<RegisteredUserDto> regs = eventService.getEventRegistrations(eventId);
        return ResponseEntity.ok(regs);
    }

    // cancel registration of particular user(Manager)
    @PatchMapping("/event/{eventId}/attendees/{attendeeId}")
    public ResponseEntity<String> cancelRegistrationOfUser(@PathVariable Long eventId, @PathVariable Long attendeeId) {
        String result = eventService.cancelRegistrationOfUser(eventId, attendeeId);
        return ResponseEntity.ok(result);
    }

    // - Cancel an event (only owner manager)
    @PatchMapping("/event/{eventId}")
    public ResponseEntity<String> cancelEvent(@PathVariable Long eventId) {
        String result = eventService.cancelEvent(eventId);
        return ResponseEntity.ok(result);
    }
}

