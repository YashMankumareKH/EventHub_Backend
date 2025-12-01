package com.cdac.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdac.dto.EventList;
import com.cdac.dto.Eventdto;
import com.cdac.dto.RegisteredUserDto;
import com.cdac.entities.Category;
import com.cdac.entities.Event;
import com.cdac.entities.EventRegistration;
import com.cdac.entities.EventStatus;
import com.cdac.entities.RegStatus;
import com.cdac.entities.User;
import com.cdac.exception.custom.CategoryNotFoundException;
import com.cdac.exception.custom.EventNotFoundException;
import com.cdac.exception.custom.InternalErrorException;
import com.cdac.exception.custom.NoDataFoundException;
import com.cdac.exception.custom.RegistrationNotFoundException;
import com.cdac.exception.custom.UserNotFoundException;
import com.cdac.repo.CategoryRepo;
import com.cdac.repo.EventRepository;
import com.cdac.repo.RegistrationRepo;
import com.cdac.repo.UserRepo;

@Service
@Transactional
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RegistrationRepo registrationRepo;

	@Override
	public List<EventList> getAllEvents() {
		List<Event> events = eventRepository.findByStatusAndIsActive(EventStatus.PUBLISHED, true);
		if (events == null || events.isEmpty()) {
			throw new NoDataFoundException("No published events available");
		}
		List<EventList> eventList = modelMapper.map(events, new TypeToken<List<EventList>>() {
		}.getType());
		return eventList;
	}

	@Override
	public String addEvent(Long managerId, Eventdto eventdto) {

		Category category = categoryRepo.findByName(eventdto.getCategoryName())
				.orElseThrow(() -> new CategoryNotFoundException("Category not found: " + eventdto.getCategoryName()));

		User user = userRepo.findById(managerId).orElseThrow(() -> new UserNotFoundException("Manager not found "));

		Event event = modelMapper.map(eventdto, Event.class);
		event.setCategory(category);
		event.setUserDetails(user);

		try {
			eventRepository.save(event);
			return "Event Added!!!";
		} catch (Exception e) {
			throw new InternalErrorException("Unexpected error while adding event");
		}
	}

	@Override
	public List<Eventdto> getUserEvents(Long id) {

		User user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));

		List<Event> events = eventRepository.findByUserDetailsAndIsActive(user, true);
		if (events == null || events.isEmpty()) {
			throw new NoDataFoundException("No events found for user");
		}

		List<Eventdto> eventList = modelMapper.map(events, new TypeToken<List<Eventdto>>() {
		}.getType());
		return eventList;
	}

	@Override
	public Eventdto getEventById(Long id) {
		Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found "));

		Category category = categoryRepo.findById(event.getCategory().getId()).orElseThrow(
				() -> new CategoryNotFoundException("Category not found with name: " + event.getCategory().getName()));

		Eventdto eventDTO = modelMapper.map(event, Eventdto.class);
		eventDTO.setCategoryName(category.getName());
		return eventDTO;
	}

	@Override
	public String updateEventDetails(Eventdto eventdto) {

		Event event = eventRepository.findById(eventdto.getId())
				.orElseThrow(() -> new EventNotFoundException("Event not found with title: " + eventdto.getTitle()));

		event.setTitle(eventdto.getTitle());
		event.setDescription(eventdto.getDescription());
		event.setStartOn(eventdto.getStartOn());
		event.setEndOn(eventdto.getEndOn());
		event.setVenue(eventdto.getVenue());
		event.setCapacity(eventdto.getCapacity());

		if (eventdto.getCategoryName() != null && !eventdto.getCategoryName().isBlank()) {
			Category category = categoryRepo.findByName(eventdto.getCategoryName()).orElseThrow(
					() -> new CategoryNotFoundException("Category not found: " + eventdto.getCategoryName()));
			event.setCategory(category);
			return "Event Updated";
		}

		throw new InternalErrorException("Unexpected error while updating event");
	}

	@Override
	public String deleteEvent(Long id) {

		Event event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found "));

		if (event.isActive()) {
			event.setActive(false);
			return "Event Deactivated";
		} else if(!event.isActive()) {
			event.setActive(true);
			return "Event Activated";
		}
		
		throw new InternalErrorException("Internal Server Error failed to Deactivate/Activate");

	}

	@Override
	public List<RegisteredUserDto> getEventRegistrations(Long eventId) {

		// ensure event exists
		eventRepository.findById(eventId)
				.orElseThrow(() -> new EventNotFoundException("Event not found "));

		List<RegisteredUserDto> registeredUser = registrationRepo.findRegisteredUsersByEventIdAndStatus(eventId,
				RegStatus.REGISTERED);

		if (registeredUser == null || registeredUser.isEmpty()) {
			throw new NoDataFoundException("No registrations found");
		}

		return registeredUser;
	}

	@Override
	public String cancelRegistrationOfUser(Long eventId, Long attendeeId) {
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new EventNotFoundException("Event not found "));
		User user = userRepo.findById(attendeeId)
				.orElseThrow(() -> new UserNotFoundException("User not found "));

		EventRegistration registration = registrationRepo.findByEventAndUserDetails(event, user);
		if (registration == null) {
			throw new RegistrationNotFoundException(
					"Registration not found for eventId: " + eventId + " and userId: " + attendeeId);
		}

		registration.setStatus(RegStatus.CANCELLED);
		try {
			registrationRepo.save(registration);
			return "Registration Cancelled";
		} catch (Exception e) {
			throw new InternalErrorException("Unexpected error while cancelling registration");
		}
	}

	@Override
	public String cancelEvent(Long eventId) {
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new EventNotFoundException("Event not found"));
		try {
			event.setStatus(EventStatus.CANCELLED);
			eventRepository.save(event);
			return "Event Cancelled";
		} catch (Exception e) {
			throw new InternalErrorException("Unexpected error while cancelling event");
		}
	}

	@Override
	public String registerForEvent(Long userId, Long eventId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found "));
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new EventNotFoundException("Event not found "));

		// optional: check duplicate registration
		EventRegistration existing = registrationRepo.findByEventAndUserDetails(event, user);
		if (existing != null && existing.getStatus() == RegStatus.REGISTERED) {
			throw new InternalErrorException("User already registered for this event");
		}

		EventRegistration registration = new EventRegistration();
		registration.setUserDetails(user);
		registration.setEvent(event);
		registration.setStatus(RegStatus.REGISTERED);

		try {
			registrationRepo.save(registration);
			return "Registration Successful";
		} catch (Exception e) {
			throw new InternalErrorException("Unexpected error while registering user for event");
		}
	}

	@Override
	public String cancelRegistrationForEvent(Long userId, Long eventId) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found "));
		Event event = eventRepository.findById(eventId)
				.orElseThrow(() -> new EventNotFoundException("Event not found "));
		EventRegistration registration = registrationRepo.findByEventAndUserDetails(event, user);
		if (registration == null) {
			throw new RegistrationNotFoundException(
					"Registration not found for eventId: " + eventId + " and userId: " + userId);
		}

		registration.setStatus(RegStatus.CANCELLED);
		try {
			registrationRepo.save(registration);
			return "Registration Cancelled";
		} catch (Exception e) {
			throw new InternalErrorException("Unexpected error while cancelling registration");
		}
	}

	@Override
	public List<Event> getEvents() {
		List<Event> eventList = eventRepository.findAll();
		if (eventList == null || eventList.isEmpty()) {
			throw new NoDataFoundException("No events present in the system");
		}
		return eventList;
	}

	@Override
	public List<Event> getAllEventsOfManager(Long managerId) {
		List<Event> eventList = eventRepository.findByUserDetailsId(managerId);
		if (eventList == null || eventList.isEmpty()) {
			throw new NoDataFoundException("No events found for manager ");
		}
		return eventList;
	}

	@Override
	public String deleteManagerEvent(Long eventId) {
		Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException("Event not found "));

		if (event.isActive()) {
			event.setActive(false);
			return "Event Deactivated";
		} 
		throw new InternalErrorException("Internal Server Error failed to Delete");
	}

}
