package com.cdac.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.cdac.dto.EventRequestDTO;
import com.cdac.dto.EventResponseDTO;
import com.cdac.service.EventService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<EventResponseDTO> getAllEvents() {
        return eventService.getAllEvents();
    }
    @GetMapping("/{eventId}")
    public EventResponseDTO getEventById(@PathVariable Long eventId) {
        return eventService.getEventById(eventId);
    }


    @PostMapping("/add/{userId}")
    public EventResponseDTO addEvent(
            @PathVariable Long userId,
            @RequestBody EventRequestDTO dto) {

        return eventService.addEvent(userId, dto);
    }

  
    @PutMapping("/{eventId}/update/{userId}")
    public EventResponseDTO updateEvent(
            @PathVariable Long eventId,
            @PathVariable Long userId,
            @RequestBody EventRequestDTO dto) {

        return eventService.updateEvent(eventId, userId, dto);
    }


    @DeleteMapping("/{eventId}/delete/{userId}")
    public String deleteEvent(
            @PathVariable Long eventId,
            @PathVariable Long userId) {

        return eventService.deleteEvent(eventId, userId);
    }
}