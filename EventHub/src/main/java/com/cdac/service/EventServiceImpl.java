package com.cdac.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.dto.EventRequestDTO;
import com.cdac.dto.EventResponseDTO;
import com.cdac.entities.Category;
import com.cdac.entities.Event;
import com.cdac.entities.EventStatus;
import com.cdac.entities.User;
import com.cdac.entities.UserRole;
import com.cdac.repo.CategoryRepo;
import com.cdac.repo.EventRepository;
import com.cdac.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;
    private final ModelMapper mapper;

    @Override
    public List<EventResponseDTO> getAllEvents() {
        return eventRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventResponseDTO getEventById(Long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        return convertToDTO(event);
    }

    @Override
    public EventResponseDTO addEvent(Long userId, EventRequestDTO dto) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // only ADMIN or ORGANIZER can create event
        if (user.getRole() != UserRole.ROLE_ADMIN &&
            user.getRole() != UserRole.ROLE_MANAGER) {
            throw new RuntimeException("Only Admin or Organizer can add events");
        }

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setOrganization(dto.getOrganization());
        event.setDescription(dto.getDescription());
        event.setCity(dto.getCity());
        event.setVenue(dto.getVenue());
        event.setStartOn(dto.getStartOn());
        event.setEndOn(dto.getEndOn());
        event.setCapacity(dto.getCapacity());
        event.setPrice(dto.getPrice());
        event.setActive(true);

        if (dto.getStatus() != null) {
            event.setStatus(EventStatus.valueOf(dto.getStatus().toUpperCase()));
        }

        event.setUserDetails(user);   // organizer
        event.setCategory(category);

        Event saved = eventRepo.save(event);
        return convertToDTO(saved);
    }

    @Override
    public EventResponseDTO updateEvent(Long eventId, Long userId, EventRequestDTO dto) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Only ADMIN or the same ORGANIZER can update
        boolean isAdmin = user.getRole() == UserRole.ROLE_ADMIN;
        boolean isOrganizer = user.getRole() == UserRole.ROLE_MANAGER &&
                              event.getUserDetails().getId().equals(userId);

        if (!isAdmin && !isOrganizer) {
            throw new RuntimeException("Not allowed to update this event");
        }

        // update allowed fields
        event.setTitle(dto.getTitle());
        event.setOrganization(dto.getOrganization());
        event.setDescription(dto.getDescription());
        event.setCity(dto.getCity());
        event.setVenue(dto.getVenue());
        event.setStartOn(dto.getStartOn());
        event.setEndOn(dto.getEndOn());
        event.setCapacity(dto.getCapacity());
        event.setPrice(dto.getPrice());

        if (dto.getStatus() != null) {
            event.setStatus(EventStatus.valueOf(dto.getStatus().toUpperCase()));
        }

        if (dto.getCategoryId() != null) {
            Category category = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            event.setCategory(category);
        }

        Event updated = eventRepo.save(event);
        return convertToDTO(updated);
    }

    @Override
    public String deleteEvent(Long eventId, Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        boolean isAdmin = user.getRole() == UserRole.ROLE_ADMIN;
        boolean isOrganizer = user.getRole() == UserRole.ROLE_MANAGER &&
                              event.getUserDetails().getId().equals(userId);

        if (!isAdmin && !isOrganizer) {
            throw new RuntimeException("Not allowed to delete this event");
        }

        eventRepo.delete(event);
        return "Event deleted successfully";
    }

    // helper to build response DTO
    private EventResponseDTO convertToDTO(Event e) {
        EventResponseDTO dto = new EventResponseDTO();
        dto.setId(e.getId());
        dto.setTitle(e.getTitle());
        dto.setOrganization(e.getOrganization());
        dto.setDescription(e.getDescription());
        dto.setCity(e.getCity());
        dto.setVenue(e.getVenue());
        dto.setStartOn(e.getStartOn());
        dto.setEndOn(e.getEndOn());
        dto.setCapacity(e.getCapacity());
        dto.setPrice(e.getPrice());
        dto.setActive(e.isActive());

        if (e.getStatus() != null) {
            dto.setStatus(e.getStatus().name());
        }

        if (e.getCategory() != null) {
            dto.setCategoryId(e.getCategory().getId());
            dto.setCategoryName(e.getCategory().getName());
        }

        if (e.getUserDetails() != null) {
            dto.setOrganizerId(e.getUserDetails().getId());
            dto.setOrganizerName(
                    e.getUserDetails().getFirstName() + " " +
                    (e.getUserDetails().getLastName() != null ? e.getUserDetails().getLastName() : "")
            );
        }

        return dto;
    }
}
