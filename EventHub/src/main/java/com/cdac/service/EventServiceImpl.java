package com.cdac.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.dto.EventList;
import com.cdac.dto.RegisteredUserDto;
import com.cdac.dto.Eventdto;
import com.cdac.entities.Category;
import com.cdac.entities.Event;
import com.cdac.entities.EventRegistration;
import com.cdac.entities.EventStatus;
import com.cdac.entities.RegStatus;
import com.cdac.entities.User;
import com.cdac.repo.CategoryRepo;
import com.cdac.repo.EventRepository;
import com.cdac.repo.RegistrationRepo;
import com.cdac.repo.UserRepo;

@Service
@Transactional

public class EventServiceImpl implements EventService {

	
	@Autowired
	private  EventRepository eventRepository;
	@Autowired
	private  ModelMapper modelMapper;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private  UserRepo userRepo;
	@Autowired
	private  RegistrationRepo registrationRepo;
	
	@Override
	public List<EventList> getAllEvents() {
		List<Event> events = eventRepository.findByStatusAndIsActive(EventStatus.PUBLISHED,true);
		List<EventList> eventList = modelMapper.map(events, new TypeToken<List<EventList>>() {
		}.getType());
		return eventList;
	}

	@Override
	public String addEvent(Eventdto eventdto) {
		
		Category category = categoryRepo.findById(eventdto.getCategoryId()).orElseThrow();
		
		Event event = modelMapper.map(eventdto, Event.class);
		
		event.setCategory(category);
		User user = userRepo.findById(7L).orElseThrow();
		event.setUserDetails(user);
		eventRepository.save(event);
		return "Event Added!!!";
	}

	@Override
	public List<Eventdto> getUserEvents(Long id) {
		
		User user = userRepo.findById(id).orElseThrow();
		
		List<Event> events = eventRepository.findByUserDetailsAndIsActive(user,true);
		List<Eventdto> eventList = modelMapper.map(events, new TypeToken<List<Eventdto>>(){}.getType());
		
		
		return eventList;
	}

	@Override
	public Eventdto getEventById(Long id) {
		Event event = eventRepository.findById(id).orElseThrow();
		Eventdto eventDTO = modelMapper.map(event, Eventdto.class);
		return eventDTO;
	}

	@Override
	public String updateEventDetails(Eventdto eventdto) {
		
		Event event = eventRepository.findById(eventdto.getId()).orElseThrow();
		
		eventRepository.save(event);
		return null;
	}

	@Override
	public String deleteEvent(Long id) {
		Event event = eventRepository.findById(id).orElseThrow();
		event.setActive(false);
		eventRepository.save(event);
		return "Deleted Successfully";
	}

	@Override
	public List<RegisteredUserDto> getEventRegistrations(Long eventId) {
		
		List<RegisteredUserDto> registeredUser = registrationRepo.
				findRegisteredUsersByEventIdAndStatus(eventId, RegStatus.REGISTERED);
		
		
		return registeredUser;
	}

	@Override
	public String cancelRegistrationOfUser(Long eventId, Long attendeeId) {
		Event event = eventRepository.findById(eventId).orElseThrow();
		User user = userRepo.findById(attendeeId).orElseThrow();
		
		EventRegistration registration = registrationRepo.findByEventAndUserDetails(event,user);
		
		registration.setStatus(RegStatus.CANCELLED);
		
		return "Registration Cancelled";
	}

	@Override
	public String cancelEvent(Long eventId) {
		Event event = eventRepository.findById(eventId).orElseThrow();
		event.setStatus(EventStatus.CANCELLED);
		
		return "Event Cancelled";
	}

	
	
}
