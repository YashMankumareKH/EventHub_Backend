package com.cdac.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.custom_exception.ApiException;
import com.cdac.custom_exception.ResourceNotFoundException;
import com.cdac.dto.RegistrationDto;
import com.cdac.entities.Event;
import com.cdac.entities.EventRegistration;
import com.cdac.entities.RegStatus;
import com.cdac.entities.User;
import com.cdac.repo.EventRegistrationRepository;
import com.cdac.repo.EventRepository;
import com.cdac.repo.UserRepo;

@Service
@Transactional
public class EventRegistrationServiceImpl implements EventRegistrationService {

    @Autowired
    private EventRegistrationRepository registrationRepo;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private EventRepository eventRepository;


    @Override
    public RegistrationDto registerForEvent(RegistrationDto dto) {

        // Validate User
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid User ID !!!"));

        // Validate Event
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Event ID !!!"));

        // Check if already registered
        if (registrationRepo.existsByUserIdAndEventId(dto.getUserId(), dto.getEventId())) {
            throw new ApiException("User already registered for this event!");
        }

        // Create Registration Entity
        EventRegistration reg = new EventRegistration();
        reg.setUser(user);
        reg.setEvent(event);
        reg.setRegistrationDate(LocalDate.now());
        reg.setRegistrationTime(LocalTime.now());
        reg.setStatus(RegStatus.REGISTERED);

        EventRegistration saved = registrationRepo.save(reg);

        // Prepare Response DTO
        RegistrationDto resp = new RegistrationDto();
        resp.setId(saved.getId());
        resp.setUserId(saved.getUser().getId());
        resp.setEventId(saved.getEvent().getId());
        resp.setRegistrationDate(saved.getRegistrationDate());
        resp.setRegistrationTime(saved.getRegistrationTime());
        resp.setStatus(saved.getStatus());

        return resp;
    }

// get user registration 
    @Override
    public List<RegistrationDto> getUserEventRegistrations(Long userId) {

        return registrationRepo.findByUserId(userId)
                .stream()
                .map(r -> {
                    RegistrationDto dto = new RegistrationDto();
                    dto.setId(r.getId());
                    dto.setUserId(r.getUser().getId());
                    dto.setEventId(r.getEvent().getId());
                    dto.setRegistrationDate(r.getRegistrationDate());
                    dto.setRegistrationTime(r.getRegistrationTime());
                    dto.setStatus(r.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }

// we find registrations  for particular event 
    @Override
    public List<RegistrationDto> getEventRegistrations(Long eventId) {

        return registrationRepo.findByEventId(eventId)
                .stream()
                .map(r -> {
                    RegistrationDto dto = new RegistrationDto();
                    dto.setId(r.getId());
                    dto.setUserId(r.getUser().getId());
                    dto.setEventId(r.getEvent().getId());
                    dto.setRegistrationDate(r.getRegistrationDate());
                    dto.setRegistrationTime(r.getRegistrationTime());
                    dto.setStatus(r.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }

/// we cancel event registeration 
    @Override
    public String cancelEventRegistration(Long registrationId) {

        EventRegistration reg = registrationRepo.findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Registration ID !!!"));

        // check if already cancelled or not 
        if (reg.getStatus() == RegStatus.CANCELLED) {
            throw new ApiException("Registration already cancelled!");
        }

        // we update status 
        reg.setStatus(RegStatus.CANCELLED);
        registrationRepo.save(reg);

        return "Event Registration Cancelled Successfully!";
    }
}
