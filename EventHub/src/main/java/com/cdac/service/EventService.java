package com.cdac.service;

import java.util.List;

import com.cdac.dto.EventRequestDTO;
import com.cdac.dto.EventResponseDTO;

public interface EventService {

    List<EventResponseDTO> getAllEvents();

    EventResponseDTO getEventById(Long eventId);

    EventResponseDTO addEvent(Long userId, EventRequestDTO dto);

    EventResponseDTO updateEvent(Long eventId, Long userId, EventRequestDTO dto);

    String deleteEvent(Long eventId, Long userId);
}
